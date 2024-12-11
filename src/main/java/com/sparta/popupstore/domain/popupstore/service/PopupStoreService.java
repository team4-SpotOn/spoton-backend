package com.sparta.popupstore.domain.popupstore.service;

import com.sparta.popupstore.domain.common.entity.Address;
import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.domain.company.entity.Company;
import com.sparta.popupstore.domain.kakaoaddress.dto.KakaoAddressApiDto;
import com.sparta.popupstore.domain.kakaoaddress.service.KakaoAddressService;
import com.sparta.popupstore.domain.popupstore.dto.request.PopupStoreCreateRequestDto;
import com.sparta.popupstore.domain.popupstore.dto.request.PopupStoreImageRequestDto;
import com.sparta.popupstore.domain.popupstore.dto.request.PopupStoreUpdateRequestDto;
import com.sparta.popupstore.domain.popupstore.dto.response.PopupStoreCreateResponseDto;
import com.sparta.popupstore.domain.popupstore.dto.response.PopupStoreFindOneResponseDto;
import com.sparta.popupstore.domain.popupstore.dto.response.PopupStoreUpdateResponseDto;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.entity.PopupStoreOperating;
import com.sparta.popupstore.domain.popupstore.repository.PopupStoreOperatingRepository;
import com.sparta.popupstore.domain.popupstore.entity.PopupStoreImage;
import com.sparta.popupstore.domain.popupstore.repository.PopupStoreRepository;
import com.sparta.popupstore.domain.user.entity.User;
import com.sparta.popupstore.domain.user.entity.UserRole;
import com.sparta.popupstore.web.WebUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PopupStoreService {

    private final PopupStoreRepository popupStoreRepository;
    private final KakaoAddressService kakaoAddressService;
    private final PopupStoreOperatingRepository popupStoreOperatingRepository;
    private final S3ImageService s3ImageService;

    // 팝업스토어 생성
    @Transactional
    public PopupStoreCreateResponseDto createPopupStore(Company company, PopupStoreCreateRequestDto requestDto) {
        PopupStore popupStore = popupStoreRepository.save(requestDto.toEntity(company));

        // 이미지 URL 추가
        popupStore.addImageList(
                requestDto.getImages()
                        .stream()
                        .map(PopupStoreImageRequestDto::toEntity)
                        .toList()
        );

        // 카카오 주소 API - 위도 경도 구하기
        KakaoAddressApiDto kakaoAddressApiDto = kakaoAddressService.getKakaoAddress(requestDto.getAddress());
        double latitude = kakaoAddressApiDto.getDocuments().get(0).getRoadAddress().getLatitude(); // 위도
        double longitude = kakaoAddressApiDto.getDocuments().get(0).getRoadAddress().getLongitude(); // 경도
        Address address = new Address(requestDto.getAddress(), latitude, longitude);
        popupStore.updateAddress(address);

        List<PopupStoreOperating> popupStoreOperatingList = new ArrayList<>();
        for (String day : requestDto.getStartTimes().keySet()) {
            DayOfWeek dayOfWeek = DayOfWeek.valueOf(day.toUpperCase());
            LocalTime startTime = requestDto.getStartTimes().get(day);
            LocalTime endTime = requestDto.getEndTimes().get(day);

            PopupStoreOperating operating = PopupStoreOperating.builder()
                    .popupStore(popupStore)
                    .dayOfWeek(dayOfWeek)
                    .startTime(startTime)
                    .endTime(endTime)
                    .build();
            popupStoreOperatingList.add(operating);
            popupStoreOperatingRepository.save(operating);
        }

        return new PopupStoreCreateResponseDto(popupStore, popupStoreOperatingList);
    }

    // 관리자 - 팝업 스토어 수정
    @Transactional
    public PopupStoreUpdateResponseDto updatePopupStore(Long popupId, PopupStoreUpdateRequestDto requestDto) {
        PopupStore popupStore = popupStoreRepository.findById(popupId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.POPUP_STORE_NOT_FOUND));

        this.updateImage(popupStore, requestDto);
        popupStore.update(requestDto);

        return new PopupStoreUpdateResponseDto(popupStoreRepository.save(popupStore.update(requestDto)), updateOperatingHours(popupStore, requestDto.getStartTimes(), requestDto.getEndTimes()));
    }

    // 회사 - 팝업 스토어 수정
    @Transactional
    public PopupStoreUpdateResponseDto updatePopupStore(Long popupId, Company company, PopupStoreUpdateRequestDto requestDto) {
        PopupStore popupStore = popupStoreRepository.findById(popupId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.POPUP_STORE_NOT_FOUND));

        if (!popupStore.getCompany().equals(company)) {
            throw new CustomApiException(ErrorCode.POPUP_STORE_NOT_BY_THIS_COMPANY);
        }

        if (!isEditable(popupStore)) {
            throw new CustomApiException(ErrorCode.POPUP_STORE_ALREADY_START);
        }

        updateOperatingHours(popupStore, requestDto.getStartTimes(), requestDto.getEndTimes());
        popupStore.update(requestDto);

        this.updateImage(popupStore, requestDto);
        popupStore.update(requestDto);
        return new PopupStoreUpdateResponseDto(popupStoreRepository.save(popupStore.update(requestDto)), updateOperatingHours(popupStore, requestDto.getStartTimes(), requestDto.getEndTimes()));
    }

    // 운영 시간 처리 로직
    private List<PopupStoreOperating> processOperatingHours(PopupStore popupStore, Map<String, LocalTime> startTimes, Map<String, LocalTime> endTimes) {
        return startTimes.keySet().stream()
                .map(day -> {
                    DayOfWeek dayOfWeek = DayOfWeek.valueOf(day.toUpperCase());
                    LocalTime startTime = startTimes.get(day);
                    LocalTime endTime = endTimes.get(day);

                    if (startTime == null && endTime == null) {
                        return PopupStoreOperating.builder()
                                .popupStore(popupStore)
                                .dayOfWeek(dayOfWeek)
                                .startTime(null)
                                .endTime(null)
                                .build();
                    }

                    if (startTime == null || endTime == null) {
                        throw new CustomApiException(ErrorCode.POPUP_STORE_OPERATING_BAD_REQUEST);
                    }

                    return PopupStoreOperating.builder()
                            .popupStore(popupStore)
                            .dayOfWeek(dayOfWeek)
                            .startTime(startTime)
                            .endTime(endTime)
                            .build();
                })
                .toList();
    }

    // 운영 시간 수정
    private List<PopupStoreOperating> updateOperatingHours(PopupStore popupStore, Map<String, LocalTime> startTimes, Map<String, LocalTime> endTimes) {
        popupStoreOperatingRepository.deleteByPopupStore(popupStore);
        return popupStoreOperatingRepository.saveAll(processOperatingHours(popupStore, startTimes, endTimes));
    }

    private void updateImage(PopupStore popupStore, PopupStoreUpdateRequestDto requestDto) {
        List<PopupStoreImage> popupStoreImageList = popupStore.getPopupStoreImageList();
        List<PopupStoreImage> requestImageList = requestDto.getImages().stream()
                                                                            .map(PopupStoreImageRequestDto::toEntity)
                                                                            .toList();
        popupStoreImageList.forEach(image -> s3ImageService.deleteImage(image.getImageUrl()));
        popupStore.updateImages(requestImageList);
    }

    // 팝업스토어 진행여부 판단
    private boolean isEditable(PopupStore popupStore) {
        return popupStore.getStartDate().isAfter(LocalDate.now());
    }

    // 팝업스토어 단건조회
    public PopupStoreFindOneResponseDto getPopupStoreOne(Long popupId, HttpServletRequest request, HttpServletResponse response){
        PopupStore popupStore = popupStoreRepository.findById(popupId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.POPUP_STORE_NOT_FOUND));

        String cookieName = "viewedPopup_" + popupId;

        Cookie cookie = WebUtil.getCookie(request, cookieName);
        if (cookie == null) {
            popupStore.viewPopupStore();
            popupStoreRepository.save(popupStore);
            WebUtil.addCookie(response, cookieName);
        }
        return new PopupStoreFindOneResponseDto(popupStore);
    }

    public void deletePopupStore(Company company, Long popupStoreId) {
        PopupStore popupStore = popupStoreRepository.findById(popupStoreId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.POPUP_STORE_NOT_FOUND));
        if(!popupStore.getCompany().getId().equals(company.getId())) {
            throw new CustomApiException(ErrorCode.POPUP_STORE_NOT_BY_THIS_COMPANY);
        }
        if(popupStore.getStartDate().isBefore(LocalDate.now())) {
            throw new CustomApiException(ErrorCode.POPUP_STORE_ALREADY_START);
        }
        popupStore.getPopupStoreImageList().forEach(image -> s3ImageService.deleteImage(image.getImageUrl()));
        popupStoreOperatingRepository.deleteByPopupStore(popupStore);
        popupStoreRepository.deleteById(popupStoreId);
    }

    public void deletePopupStore(Long popupStoreId) {
        PopupStore popupStore = popupStoreRepository.findById(popupStoreId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.POPUP_STORE_NOT_FOUND));
        popupStore.getPopupStoreImageList().forEach(image -> s3ImageService.deleteImage(image.getImageUrl()));
        popupStoreOperatingRepository.deleteByPopupStore(popupStore);
        popupStoreRepository.deleteById(popupStoreId);
    }

    // 임시 팝업 스토어 전체목록(지도용)
    public List<PopupStoreFindOneResponseDto> getPopupStoreAll(){
        return popupStoreRepository.findAll().stream().map(PopupStoreFindOneResponseDto::new).toList();
    }
}
