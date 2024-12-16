package com.sparta.popupstore.domain.popupstore.service;

import com.sparta.popupstore.domain.popupstore.dto.PopupStoreBundle;
import com.sparta.popupstore.domain.popupstore.dto.request.PopupStoreAttributeRequestDto;
import com.sparta.popupstore.domain.popupstore.dto.request.PopupStoreImageRequestDto;
import com.sparta.popupstore.domain.popupstore.dto.request.PopupStoreOperatingRequestDto;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.repository.PopupStoreAttributeRepository;
import com.sparta.popupstore.domain.popupstore.repository.PopupStoreImageRepository;
import com.sparta.popupstore.domain.popupstore.repository.PopupStoreOperatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
