package com.sparta.popupstore.domain.popupstore.service;

import com.sparta.popupstore.domain.common.entity.Address;
import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.domain.company.entity.Company;
import com.sparta.popupstore.domain.kakaoaddress.dto.KakaoAddressApiDto;
import com.sparta.popupstore.domain.kakaoaddress.service.KakaoAddressService;
import com.sparta.popupstore.domain.popupstore.dto.request.PopupStoreCreateRequestDto;
import com.sparta.popupstore.domain.popupstore.dto.request.PopupStoreUpdateRequestDto;
import com.sparta.popupstore.domain.popupstore.dto.response.PopupStoreCreateResponseDto;
import com.sparta.popupstore.domain.popupstore.dto.response.PopupStoreFindOneResponseDto;
import com.sparta.popupstore.domain.popupstore.dto.response.PopupStoreUpdateResponseDto;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.repository.PopupStoreAttributeRepository;
import com.sparta.popupstore.domain.popupstore.repository.PopupStoreImageRepository;
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

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PopupStoreService {

    private final PopupStoreRepository popupStoreRepository;
    private final PopupStoreOperatingRepository popupStoreOperatingRepository;
    private final PopupStoreAttributeRepository popupStoreAttributesRepository;
    private final PopupStoreImageRepository popupStoreImageRepository;
    private final KakaoAddressService kakaoAddressService;
    private final S3ImageService s3ImageService;

    // 팝업스토어 생성
    @Transactional
    public PopupStoreCreateResponseDto createPopupStore(Company company, PopupStoreCreateRequestDto requestDto) {
        // 카카오 주소 API - 위도 경도 구하기
        KakaoAddressApiDto kakaoAddressApiDto = kakaoAddressService.getKakaoAddress(requestDto.getAddress());
        Address address = new Address(requestDto.getAddress(), kakaoAddressApiDto);

        PopupStore popupStore = popupStoreRepository.save(requestDto.toEntity(company, address));


        // 이미지 URL 추가
        var imageList = popupStoreImageRepository.saveAll(
                requestDto.getImageList().stream()
                        .map(imageDto -> imageDto.toEntity(popupStore))
                        .toList()
        );

        // 팝업스토어 운영 시간 저장
        var operatingList = popupStoreOperatingRepository.saveAll(
                requestDto.getOperatingList().stream()
                        .map(operationDto -> operationDto.toEntity(popupStore))
                        .toList()
        );

        // 속성 설정
        var attributeList = popupStoreAttributesRepository.saveAll(
                requestDto.getAttributeList().stream()
                        .map(attributeDto -> attributeDto.toEntity(popupStore))
                        .toList()
        );

        return new PopupStoreCreateResponseDto(popupStore, imageList, operatingList, attributeList);
    }

    // 팝업스토어 단건조회
    public PopupStoreFindOneResponseDto getPopupStoreOne(Long popupId, HttpServletRequest request, HttpServletResponse response) {
        PopupStore popupStore = popupStoreRepository.findById(popupId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.POPUP_STORE_NOT_FOUND));

        String cookieName = "viewedPopup_" + popupId;

        Cookie cookie = WebUtil.getCookie(request, cookieName);
        if(cookie == null) {
            popupStore.viewPopupStore();
            popupStoreRepository.save(popupStore);
            WebUtil.addCookie(response, cookieName);
        }

        var imageList = popupStoreImageRepository.findByPopupStore(popupStore);
        return new PopupStoreFindOneResponseDto(popupStore, imageList);
    }

    // 임시 팝업 스토어 전체목록(지도용)
    public List<PopupStoreFindOneResponseDto> getPopupStoreAll() {
        return popupStoreRepository.findAll().stream()
                .map(popupStore -> {
                    var imageList = popupStoreImageRepository.findByPopupStore(popupStore);
                    return new PopupStoreFindOneResponseDto(popupStore, imageList);
                })
                .toList();
    }

    // 회사 - 팝업 스토어 수정
    @Transactional
    public PopupStoreUpdateResponseDto updatePopupStore(Long popupId, Company company, PopupStoreUpdateRequestDto requestDto) {
        PopupStore popupStore = popupStoreRepository.findById(popupId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.POPUP_STORE_NOT_FOUND));
        if(!popupStore.getCompany().equals(company)) {
            throw new CustomApiException(ErrorCode.POPUP_STORE_NOT_BY_THIS_COMPANY);
        }
        if(isNotEditable(popupStore)) {
            throw new CustomApiException(ErrorCode.POPUP_STORE_ALREADY_START);
        }

        return updatePopupStore(popupStore, requestDto);
    }

    // 관리자 - 팝업 스토어 수정
    @Transactional
    public PopupStoreUpdateResponseDto updatePopupStore(Long popupId, PopupStoreUpdateRequestDto requestDto) {
        PopupStore popupStore = popupStoreRepository.findById(popupId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.POPUP_STORE_NOT_FOUND));

        return updatePopupStore(popupStore, requestDto);
    }

    PopupStoreUpdateResponseDto updatePopupStore(PopupStore popupStore, PopupStoreUpdateRequestDto requestDto) {
        KakaoAddressApiDto kakaoAddressApiDto = kakaoAddressService.getKakaoAddress(requestDto.getAddress());
        Address address = new Address(requestDto.getAddress(), kakaoAddressApiDto);

        popupStore.update(requestDto, address);

        popupStoreImageRepository
                .findByPopupStore(popupStore)
                .forEach(image -> {
                    s3ImageService.deleteImage(image.getImageUrl());
                    popupStoreImageRepository.delete(image);
                });

        var imageList = popupStoreImageRepository.saveAll(
                requestDto.getImageList().stream()
                        .map(imageDto -> imageDto.toEntity(popupStore))
                        .toList()
        );

        var operatingList = requestDto.getOperatingList().stream()
                .map(operatingDto -> popupStoreOperatingRepository
                        .findByPopupStoreAndDayOfWeek(popupStore, operatingDto.getDayOfWeek())
                        .orElseThrow(() -> new CustomApiException(ErrorCode.POPUP_STORE_OPERATING_BAD_REQUEST))
                        .update(operatingDto.getStartTime(), operatingDto.getEndTime())
                )
                .toList();

        popupStoreAttributesRepository.deleteByPopupStore(popupStore);
        var attributeList = popupStoreAttributesRepository.saveAll(
                requestDto.getAttributeList().stream()
                        .map(attributeDto -> attributeDto.toEntity(popupStore))
                        .toList()
        );

        return new PopupStoreUpdateResponseDto(popupStore, imageList, operatingList, attributeList);
    }

    public void deletePopupStore(Company company, Long popupStoreId) {
        PopupStore popupStore = popupStoreRepository.findById(popupStoreId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.POPUP_STORE_NOT_FOUND));
        if(!popupStore.getCompany().getId().equals(company.getId())) {
            throw new CustomApiException(ErrorCode.POPUP_STORE_NOT_BY_THIS_COMPANY);
        }
        if(isNotEditable(popupStore)) {
            throw new CustomApiException(ErrorCode.POPUP_STORE_ALREADY_START);
        }

        deletePopupStore(popupStore);
    }

    public void deletePopupStore(Long popupStoreId) {
        PopupStore popupStore = popupStoreRepository.findById(popupStoreId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.POPUP_STORE_NOT_FOUND));

        deletePopupStore(popupStore);
    }

    private void deletePopupStore(PopupStore popupStore) {
        popupStoreImageRepository.findByPopupStore(popupStore)
                .forEach(image -> {
                    s3ImageService.deleteImage(image.getImageUrl());
                    popupStoreImageRepository.delete(image);
                });
        popupStoreOperatingRepository.deleteByPopupStore(popupStore);
        popupStoreAttributesRepository.deleteByPopupStore(popupStore);
        popupStoreRepository.delete(popupStore);
    }

    // 팝업스토어 진행여부 판단
    private boolean isNotEditable(PopupStore popupStore) {
        return popupStore.getStartDate().isBefore(LocalDate.now());
    }

}
