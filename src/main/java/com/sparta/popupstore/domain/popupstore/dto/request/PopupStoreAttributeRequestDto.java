package com.sparta.popupstore.domain.popupstore.dto.request;

import com.sparta.popupstore.domain.popupstore.entity.PopupStoreAttributeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PopupStoreAttributeRequestDto {
    private PopupStoreAttributeEnum attribute;
    private Boolean isAllow;
}
