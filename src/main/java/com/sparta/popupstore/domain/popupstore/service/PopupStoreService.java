package com.sparta.popupstore.domain.popupstore.service;

import com.sparta.popupstore.domain.common.entity.Address;
import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.domain.company.entity.Company;
import com.sparta.popupstore.domain.kakaoaddress.service.KakaoAddressService;
import com.sparta.popupstore.domain.popupstore.dto.request.PopupStoreCreateRequestDto;
import com.sparta.popupstore.domain.popupstore.dto.request.PopupStoreUpdateRequestDto;
import com.sparta.popupstore.domain.popupstore.dto.response.*;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.repository.PopupStoreRepository;
import com.sparta.popupstore.domain.popupstore.enums.PopupStoreStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PopupStoreService {

    private final PopupStoreRepository popupStoreRepository;
    private final KakaoAddressService kakaoAddressService;
    private final PopupStoreBundleService popupStoreBundleService;

    // 팝업스토어 생성
    @Transactional
    public PopupStoreCreateResponseDto createPopupStore(Company company, PopupStoreCreateRequestDto requestDto) {
        // 카카오 주소 API - 위도 경도 구하기
        Address address = kakaoAddressService.getKakaoAddress(requestDto.getAddress());
        PopupStore popupStore = popupStoreRepository.save(requestDto.toEntity(company, address));

        var popupStoreBundle = popupStoreBundleService.createPopupStoreBundle(
                popupStore,
                requestDto.getImageList(),
                requestDto.getOperatingList(),
                requestDto.getAttributeList()
        );

        return new PopupStoreCreateResponseDto(
                popupStore,
                popupStoreBundle.getImageList(),
                popupStoreBundle.getOperatingList(),
                popupStoreBundle.getAttributeList()
        );
    }

    // 팝업스토어 단건조회
    public PopupStoreGetOneResponseDto getPopupStoreOne(Long popupStoreId, boolean view) {
        PopupStore popupStore = popupStoreRepository.findById(popupStoreId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.POPUP_STORE_NOT_FOUND));

        if(view) {
            popupStore.viewPopupStore();
        }
        var popupStoreBundle = popupStoreBundleService.getPopupStoreBundle(popupStore);
        return new PopupStoreGetOneResponseDto(
                popupStore,
                popupStoreBundle.getImageList(),
                popupStoreBundle.getOperatingList(),
                popupStoreBundle.getAttributeList()
        );
    }

    // 임시 팝업 스토어 전체목록(지도용)
    public List<PopupStoreSearchResponseDto> getPopupStoreAll() {
        return popupStoreRepository.findAll().stream()
                .map(PopupStoreSearchResponseDto::new)
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

    private PopupStoreUpdateResponseDto updatePopupStore(PopupStore popupStore, PopupStoreUpdateRequestDto requestDto) {
        Address address = kakaoAddressService.getKakaoAddress(requestDto.getAddress());

        popupStore.update(
                requestDto.getName(),
                requestDto.getContents(),
                requestDto.getPrice(),
                address,
                requestDto.getStartDate(),
                requestDto.getEndDate()
        );

        var popupStoreBundle = popupStoreBundleService.updatePopupStoreBundle(
                popupStore,
                requestDto.getImageList(),
                requestDto.getOperatingList(),
                requestDto.getAttributeList()
        );

        return new PopupStoreUpdateResponseDto(
                popupStore,
                popupStoreBundle.getImageList(),
                popupStoreBundle.getOperatingList(),
                popupStoreBundle.getAttributeList()
        );
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
        popupStoreBundleService.deletePopupStoreBundle(popupStore);
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

    public Page<PopupStoreSearchResponseDto> getPopupStoreByStatus(int page, int size, PopupStoreStatus popupStoreStatus) {
        Pageable pageable = PageRequest.of(page-1, size);
        return popupStoreRepository.findByStatus(pageable, popupStoreStatus).map(PopupStoreSearchResponseDto::new);
    }

    public List<PopupStoreSearchResponseDto> findStorePeriod(LocalDate startDate, LocalDate endDate, Long page, Long size) {
        return popupStoreRepository.findByStartDateAndEndDate(startDate, endDate, page, size)
                .stream().map(PopupStoreSearchResponseDto::new).toList();
    }
}
