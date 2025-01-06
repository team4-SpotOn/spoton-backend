package com.sparta.popupstore.domain.popupstore.bundle.dto.request;

import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.bundle.entity.PopupStoreAttribute;
import com.sparta.popupstore.domain.popupstore.bundle.enums.PopupStoreAttributeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PopupStoreAttributeRequestDto {
    private PopupStoreAttributeEnum attribute;
    private Boolean isAllow;

    public PopupStoreAttribute toEntity(PopupStore popupStore) {
        return PopupStoreAttribute.builder()
                .popupStore(popupStore)
                .attribute(this.attribute)
                .isAllow(this.isAllow)
                .build();
    }
}
