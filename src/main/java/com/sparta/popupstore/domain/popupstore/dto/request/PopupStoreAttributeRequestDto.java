package com.sparta.popupstore.domain.popupstore.dto.request;

import com.sparta.popupstore.domain.popupstore.entity.PopupStoreAttribute;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PopupStoreAttributeRequestDto {
    private PopupStoreAttribute attribute;
    private Boolean isAllow;
}
