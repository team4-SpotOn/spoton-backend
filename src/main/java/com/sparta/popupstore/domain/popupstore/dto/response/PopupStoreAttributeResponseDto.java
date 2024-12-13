package com.sparta.popupstore.domain.popupstore.dto.response;

import com.sparta.popupstore.domain.popupstore.entity.PopupStoreAttribute;
import com.sparta.popupstore.domain.popupstore.entity.PopupStoreAttributeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class PopupStoreAttributeResponseDto {
    @Schema(description = "속성 타입")
    private final PopupStoreAttributeEnum attribute;
    @Schema(description = "허용 여부")
    private final Boolean isAllow;

    public PopupStoreAttributeResponseDto(PopupStoreAttribute popupStoreAttribute) {
        this.attribute = popupStoreAttribute.getAttribute();
        this.isAllow = popupStoreAttribute.getIsAllow();
    }
}