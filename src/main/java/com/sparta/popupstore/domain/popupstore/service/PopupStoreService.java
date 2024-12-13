package com.sparta.popupstore.domain.popupstore.service;

import com.sparta.popupstore.domain.common.entity.Address;
import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.domain.company.entity.Company;
import com.sparta.popupstore.domain.kakaoaddress.service.KakaoAddressService;
import com.sparta.popupstore.domain.popupstore.dto.request.PopupStoreCreateRequestDto;
import com.sparta.popupstore.domain.popupstore.dto.request.PopupStoreUpdateRequestDto;
import com.sparta.popupstore.domain.popupstore.dto.response.PopupStoreCreateResponseDto;
import com.sparta.popupstore.domain.popupstore.dto.response.PopupStoreGetResponseDto;
import com.sparta.popupstore.domain.popupstore.dto.response.PopupStoreUpdateResponseDto;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.repository.PopupStoreRepository;
import com.sparta.popupstore.domain.popupstore.util.AttributeUtil;
import com.sparta.popupstore.domain.popupstore.util.ImageUtil;
import com.sparta.popupstore.domain.popupstore.util.OperatingUtil;
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
        Address address = kakaoAddressService.getKakaoAddress(requestDto.getAddress());

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
    public PopupStoreGetResponseDto getPopupStoreOne(Long popupStoreId, boolean view) {
        PopupStore popupStore = popupStoreRepository.findById(popupStoreId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.POPUP_STORE_NOT_FOUND));

        if(view) {
            popupStore.viewPopupStore();
        }
        return getPopupStore(popupStore);
    }

    // 임시 팝업 스토어 전체목록(지도용)
    public List<PopupStoreGetResponseDto> getPopupStoreAll() {
        return popupStoreRepository.findAll().stream()
                .map(this::getPopupStore)
                .toList();
    }

    private PopupStoreGetResponseDto getPopupStore(PopupStore popupStore) {
        var imageList = imageUtil.getPopupStoreImageList(popupStore);
        var operatingList = operatingUtil.getPopupStoreOperatingList(popupStore);
        var attributeList = attributeUtil.getPopupStoreAttributeList(popupStore);
        return new PopupStoreGetResponseDto(popupStore, imageList, operatingList, attributeList);
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

    private PopupStoreUpdateResponseDto updatePopupStore(PopupStore popupStore, PopupStoreUpdateRequestDto requestDto) {
        Address address = kakaoAddressService.getKakaoAddress(requestDto.getAddress());

        popupStore.update(
                requestDto.getName(),
                requestDto.getContents(),
                Integer.parseInt(requestDto.getPrice()),
                address,
                requestDto.getStartDate(),
                requestDto.getEndDate()
        );

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
