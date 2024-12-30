package com.sparta.popupstore.domain.popupstore.bundle.service;

import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.domain.popupstore.bundle.entity.PopupStoreAttribute;
import com.sparta.popupstore.domain.popupstore.bundle.entity.PopupStoreBundle;
import com.sparta.popupstore.domain.popupstore.bundle.dto.request.PopupStoreAttributeRequestDto;
import com.sparta.popupstore.domain.popupstore.bundle.dto.request.PopupStoreImageRequestDto;
import com.sparta.popupstore.domain.popupstore.bundle.dto.request.PopupStoreOperatingRequestDto;
import com.sparta.popupstore.domain.popupstore.bundle.entity.PopupStoreOperating;
import com.sparta.popupstore.domain.popupstore.bundle.enums.PopupStoreAttributeEnum;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.bundle.repository.PopupStoreAttributeRepository;
import com.sparta.popupstore.domain.popupstore.bundle.repository.PopupStoreImageRepository;
import com.sparta.popupstore.domain.popupstore.bundle.repository.PopupStoreOperatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PopupStoreBundleService {

    private final PopupStoreImageRepository popupStoreImageRepository;
    private final PopupStoreOperatingRepository popupStoreOperatingRepository;
    private final PopupStoreAttributeRepository popupStoreAttributeRepository;

    public PopupStoreBundle createPopupStoreBundle(
            PopupStore popupStore,
            List<PopupStoreImageRequestDto> imageDtoList,
            List<PopupStoreOperatingRequestDto> operatingDtoList,
            List<PopupStoreAttributeRequestDto> attributeDtoList
    ) {
        var imageList = popupStoreImageRepository.saveAll(
                imageDtoList.stream()
                        .map(imageDto -> imageDto.toEntity(popupStore))
                        .toList()
        );
        var operatingList = popupStoreOperatingRepository.saveAll(
                operatingDtoList.stream()
                        .map(operationDto -> operationDto.toEntity(popupStore))
                        .toList()
        );
        var attributeList = popupStoreAttributeRepository.saveAll(
                attributeDtoList.stream()
                        .map(attributeDto -> attributeDto.toEntity(popupStore))
                        .toList()
        );

        return new PopupStoreBundle(imageList, operatingList, attributeList);
    }

    public PopupStoreBundle getPopupStoreBundle(
            PopupStore popupStore
    ) {
        var imageList = popupStoreImageRepository.findAllByPopupStore(popupStore);
        var operatingList = popupStoreOperatingRepository.findAllByPopupStore(popupStore);
        var attributeList = popupStoreAttributeRepository.findAllByPopupStore(popupStore);
        return new PopupStoreBundle(imageList, operatingList, attributeList);
    }

    public PopupStoreBundle updatePopupStoreBundle(
            PopupStore popupStore,
            List<PopupStoreImageRequestDto> imageDtoList,
            List<PopupStoreOperatingRequestDto> operatingDtoList,
            List<PopupStoreAttributeRequestDto> attributeDtoList
    ) {
        deletePopupStoreBundle(popupStore);
        return createPopupStoreBundle(popupStore, imageDtoList, operatingDtoList, attributeDtoList);
    }

    public void deletePopupStoreBundle(
            PopupStore popupStore
    ) {
        popupStoreImageRepository.deleteAllByPopupStore(popupStore);
        popupStoreOperatingRepository.deleteAllByPopupStore(popupStore);
        popupStoreAttributeRepository.deleteAllByPopupStore(popupStore);
    }

    public void reservationValid(PopupStore popupStore, LocalDateTime reservationAt) {
        PopupStoreAttribute popupStoreAttribute = popupStoreAttributeRepository
                .findByPopupStoreAndAttribute(popupStore, PopupStoreAttributeEnum.RESERVATION)
                .orElseThrow(() -> new CustomApiException(ErrorCode.POPUP_STORE_CAN_NOT_RESERVATION));
        if(!popupStoreAttribute.getIsAllow()) {
            throw new CustomApiException(ErrorCode.POPUP_STORE_CAN_NOT_RESERVATION);
        }

        PopupStoreOperating operating = popupStoreOperatingRepository
                .findByPopupStoreAndDayOfWeek(popupStore, reservationAt.getDayOfWeek())
                .orElseThrow(() -> new CustomApiException(ErrorCode.CAN_NOT_RESERVATION_AT));
        if(reservationAt.toLocalTime().isBefore(operating.getStartTime())
                || reservationAt.toLocalTime().isAfter(operating.getEndTime())) {
            throw new CustomApiException(ErrorCode.CAN_NOT_RESERVATION_AT);
        }
    }
}
