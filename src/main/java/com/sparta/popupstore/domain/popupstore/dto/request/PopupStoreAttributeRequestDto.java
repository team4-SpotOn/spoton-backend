package com.sparta.popupstore.domain.popupstore.dto.request;

import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.entity.PopupStoreAttribute;
import com.sparta.popupstore.domain.popupstore.entity.PopupStoreAttributeEnum;
import lombok.Getter;

@Getter
public class PopupStoreAttributeRequestDto {
    private PopupStoreAttributeEnum attribute;
    private Boolean isAllow;

    public PopupStoreAttribute toEntity(PopupStore popupStore) {
        return PopupStoreAttribute.builder()
                .popupStore(popupStore)
                .attribute(this.attribute)
                .isAllow(isAllow)
                .build();
    }
}
