package com.sparta.popupstore.domain.popupstore.service;

import com.sparta.popupstore.domain.common.entity.Address;
import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.domain.company.entity.Company;
import com.sparta.popupstore.domain.kakaoaddress.dto.KakaoAddressApiDto;
import com.sparta.popupstore.domain.kakaoaddress.service.KakaoAddressService;
import com.sparta.popupstore.domain.popupstore.dto.request.PopupStoreAttributeRequestDto;
import com.sparta.popupstore.domain.popupstore.dto.request.PopupStoreCreateRequestDto;
import com.sparta.popupstore.domain.popupstore.dto.request.PopupStoreImageRequestDto;
import com.sparta.popupstore.domain.popupstore.dto.request.PopupStoreUpdateRequestDto;
import com.sparta.popupstore.domain.popupstore.dto.response.PopupStoreCreateResponseDto;
import com.sparta.popupstore.domain.popupstore.dto.response.PopupStoreFindOneResponseDto;
import com.sparta.popupstore.domain.popupstore.dto.response.PopupStoreUpdateResponseDto;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.entity.PopupStoreAttributes;
import com.sparta.popupstore.domain.popupstore.entity.PopupStoreImage;
import com.sparta.popupstore.domain.popupstore.entity.PopupStoreOperating;
import com.sparta.popupstore.domain.popupstore.repository.PopupStoreAttributeRepository;
import com.sparta.popupstore.domain.popupstore.repository.PopupStoreOperatingRepository;
import com.sparta.popupstore.domain.popupstore.repository.PopupStoreRepository;
import com.sparta.popupstore.s3.service.S3ImageService;
import com.sparta.popupstore.web.WebUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PopupStoreService {

    private final PopupStoreRepository popupStoreRepository;
    private final KakaoAddressService kakaoAddressService;
    private final PopupStoreOperatingRepository popupStoreOperatingRepository;
    private final S3ImageService s3ImageService;
    private final PopupStoreOperatingService popupStoreOperatingService;
    private final PopupStoreAttributeRepository popupStoreAttributesRepository;

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
        Address address = new Address(requestDto.getAddress(), kakaoAddressApiDto);
        popupStore.updateAddress(address);

        // 팝업스토어 운영 시간 저장
        List<PopupStoreOperating> operatingList = Arrays.stream(DayOfWeek.values())
                .map(dayOfWeek -> popupStoreOperatingService.createOperatingHours(popupStore, dayOfWeek, requestDto.getStartTimes(), requestDto.getEndTimes()))
                .filter(Objects::nonNull)
                .toList();

        // 속성 설정
        updateAttributes(popupStore.getId(), requestDto.getAttributes());

        return new PopupStoreCreateResponseDto(popupStore, operatingList, popupStoreAttributesRepository.findByPopupStoreId(popupStore.getId()));
    }

    // 관리자 - 팝업 스토어 수정
    @Transactional
    public PopupStoreUpdateResponseDto updatePopupStore(Long popupId, PopupStoreUpdateRequestDto requestDto) {
        PopupStore popupStore = popupStoreRepository.findById(popupId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.POPUP_STORE_NOT_FOUND));

        List<PopupStoreOperating> operatingList = Arrays.stream(DayOfWeek.values())
                .map(dayOfWeek -> popupStoreOperatingService.createOperatingHours(popupStore, dayOfWeek, requestDto.getStartTimes(), requestDto.getEndTimes()))
                .filter(Objects::nonNull)
                .toList();

        popupStoreOperatingRepository.deleteByPopupStore(popupStore);
        this.updateImage(popupStore, requestDto);
        updateAttributes(popupStore.getId(), requestDto.getAttributes());

        return new PopupStoreUpdateResponseDto(popupStore.update(requestDto), operatingList, popupStoreAttributesRepository.findByPopupStoreId(popupId));
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

        List<PopupStoreOperating> operatingList = Arrays.stream(DayOfWeek.values())
                .map(dayOfWeek -> popupStoreOperatingService.createOperatingHours(popupStore, dayOfWeek, requestDto.getStartTimes(), requestDto.getEndTimes()))
                .filter(Objects::nonNull)
                .toList();
        popupStoreOperatingRepository.deleteByPopupStore(popupStore);
        this.updateImage(popupStore, requestDto);
        updateAttributes(popupStore.getId(), requestDto.getAttributes());

        return new PopupStoreUpdateResponseDto(popupStore.update(requestDto), operatingList, popupStoreAttributesRepository.findByPopupStoreId(popupId));
    }

    private void updateImage(PopupStore popupStore, PopupStoreUpdateRequestDto requestDto) {
        List<PopupStoreImage> popupStoreImageList = popupStore.getPopupStoreImageList();
        List<PopupStoreImage> requestImageList = requestDto.getImages().stream()
                .map(PopupStoreImageRequestDto::toEntity)
                .toList();
        popupStoreImageList.forEach(image -> s3ImageService.deleteImage(image.getImageUrl()));
        popupStore.updateImages(requestImageList);
    }

    // 속성 업데이트 메서드
    private void updateAttributes(Long popupStoreId, List<PopupStoreAttributeRequestDto> attributeDtos) {
        popupStoreAttributesRepository.deleteByPopupStoreId(popupStoreId);
        List<PopupStoreAttributes> newAttributes = attributeDtos.stream()
                .map(dto -> new PopupStoreAttributes(popupStoreRepository.getOne(popupStoreId), dto.getAttribute(), dto.getIsAllow()))
                .toList();
        popupStoreAttributesRepository.saveAll(newAttributes);
    }

    // 팝업스토어 진행여부 판단
    private boolean isEditable(PopupStore popupStore) {
        return popupStore.getStartDate().isAfter(LocalDate.now());
    }

    // 팝업스토어 단건조회
    public PopupStoreFindOneResponseDto getPopupStoreOne(Long popupId, HttpServletRequest request, HttpServletResponse response) {
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
        if (!popupStore.getCompany().getId().equals(company.getId())) {
            throw new CustomApiException(ErrorCode.POPUP_STORE_NOT_BY_THIS_COMPANY);
        }
        if (!isEditable(popupStore)) {
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
    public List<PopupStoreFindOneResponseDto> getPopupStoreAll() {
        return popupStoreRepository.findAll().stream().map(PopupStoreFindOneResponseDto::new).toList();
    }
}
