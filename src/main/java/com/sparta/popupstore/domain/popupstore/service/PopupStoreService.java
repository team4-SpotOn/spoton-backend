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
import com.sparta.popupstore.domain.popupstore.repository.PopupStoreRepository;
import com.sparta.popupstore.domain.popupstore.util.AttributeUtil;
import com.sparta.popupstore.domain.popupstore.util.ImageUtil;
import com.sparta.popupstore.domain.popupstore.util.OperatingUtil;
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
    private final KakaoAddressService kakaoAddressService;
    private final ImageUtil imageUtil;
    private final OperatingUtil operatingUtil;
    private final AttributeUtil attributeUtil;

    // 팝업스토어 생성
    @Transactional
    public PopupStoreCreateResponseDto createPopupStore(Company company, PopupStoreCreateRequestDto requestDto) {
        // 카카오 주소 API - 위도 경도 구하기
        KakaoAddressApiDto kakaoAddressApiDto = kakaoAddressService.getKakaoAddress(requestDto.getAddress());
        Address address = new Address(requestDto.getAddress(), kakaoAddressApiDto);

        PopupStore popupStore = popupStoreRepository.save(requestDto.toEntity(company, address));

        // 이미지 URL 추가
        var imageList = imageUtil.createPopupStoreImageList(popupStore, requestDto.getImageList());

        // 팝업스토어 운영 시간 저장
        var operatingList = operatingUtil.createPopupStoreOperatingList(popupStore, requestDto.getOperatingList());

        // 속성 설정
        var attributeList = attributeUtil.createPopupStoreAttributeList(popupStore, requestDto.getAttributeList());

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

        var imageList = imageUtil.getPopupStoreImageList(popupStore);
        var operatingList = operatingUtil.getPopupStoreOperatingList(popupStore);
        var attributeList = attributeUtil.getPopupStoreAttributeList(popupStore);
        return new PopupStoreFindOneResponseDto(popupStore, imageList, operatingList, attributeList);
    }

    // 임시 팝업 스토어 전체목록(지도용)
    public List<PopupStoreFindOneResponseDto> getPopupStoreAll() {
        return popupStoreRepository.findAll().stream()
                .map(popupStore -> {
                    var imageList = imageUtil.getPopupStoreImageList(popupStore);
                    var operatingList = operatingUtil.getPopupStoreOperatingList(popupStore);
                    var attributeList = attributeUtil.getPopupStoreAttributeList(popupStore);
                    return new PopupStoreFindOneResponseDto(popupStore, imageList, operatingList, attributeList);
                })
                .toList();
    }

    // 회사 - 팝업 스토어 수정
    @Transactional
    public PopupStoreUpdateResponseDto updatePopupStore(Long popupId, Company company, PopupStoreUpdateRequestDto requestDto) {
        PopupStore popupStore = popupStoreRepository.findById(popupId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.POPUP_STORE_NOT_FOUND));
        isEditable(company, popupStore);

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

        imageUtil.deletePopupStoreImageList(popupStore);
        var imageList = imageUtil.createPopupStoreImageList(popupStore, requestDto.getImageList());

        operatingUtil.deletePopupStoreOperatingList(popupStore);
        var operatingList = operatingUtil.createPopupStoreOperatingList(popupStore, requestDto.getOperatingList());

        attributeUtil.deletePopupAttributeList(popupStore);
        var attributeList = attributeUtil.createPopupStoreAttributeList(popupStore, requestDto.getAttributeList());

        return new PopupStoreUpdateResponseDto(popupStore, imageList, operatingList, attributeList);
    }

    public void deletePopupStore(Company company, Long popupStoreId) {
        PopupStore popupStore = popupStoreRepository.findById(popupStoreId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.POPUP_STORE_NOT_FOUND));
        isEditable(company, popupStore);

        deletePopupStore(popupStore);
    }

    public void deletePopupStore(Long popupStoreId) {
        PopupStore popupStore = popupStoreRepository.findById(popupStoreId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.POPUP_STORE_NOT_FOUND));

        deletePopupStore(popupStore);
    }

    private void deletePopupStore(PopupStore popupStore) {
        imageUtil.deletePopupStoreImageList(popupStore);
        operatingUtil.deletePopupStoreOperatingList(popupStore);
        attributeUtil.deletePopupAttributeList(popupStore);
        popupStoreRepository.delete(popupStore);
    }

    // 팝업스토어 진행여부 판단
    private void isEditable(Company company, PopupStore popupStore) {
        if(!popupStore.getCompany().getId().equals(company.getId())) {
            throw new CustomApiException(ErrorCode.POPUP_STORE_NOT_BY_THIS_COMPANY);
        }
        if(popupStore.getStartDate().isBefore(LocalDate.now())) {
            throw new CustomApiException(ErrorCode.POPUP_STORE_ALREADY_START);
        }
    }
}
