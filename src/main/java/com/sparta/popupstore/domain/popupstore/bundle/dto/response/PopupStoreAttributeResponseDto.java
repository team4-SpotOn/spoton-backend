package com.sparta.popupstore.domain.popupstore.bundle.dto.response;

import com.sparta.popupstore.domain.popupstore.bundle.entity.PopupStoreAttribute;
import com.sparta.popupstore.domain.popupstore.bundle.enums.PopupStoreAttributeEnum;
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
