package com.sparta.popupstore.domain.popupstore.util;

import com.sparta.popupstore.domain.popupstore.dto.request.PopupStoreAttributeRequestDto;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.entity.PopupStoreAttribute;
import com.sparta.popupstore.domain.popupstore.repository.PopupStoreAttributeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AttributeUtil {

    private final PopupStoreAttributeRepository popupStoreAttributeRepository;

    public List<PopupStoreAttribute> createPopupStoreAttributeList(
            PopupStore popupStore,
            List<PopupStoreAttributeRequestDto> attributeDtoList
    ) {
        return popupStoreAttributeRepository.saveAll(
                attributeDtoList.stream()
                        .map(attributeDto -> attributeDto.toEntity(popupStore))
                        .toList()
        );
    }

    public List<PopupStoreAttribute> getPopupStoreAttributeList(PopupStore popupStore) {
        return popupStoreAttributeRepository.findByPopupStore(popupStore);
    }

    public void deletePopupAttributeList(PopupStore popupStore) {
        popupStoreAttributeRepository.deleteByPopupStore(popupStore);
    }
}
