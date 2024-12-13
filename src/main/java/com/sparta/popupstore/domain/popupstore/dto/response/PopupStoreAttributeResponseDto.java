package com.sparta.popupstore.domain.popupstore.dto.response;

import com.sparta.popupstore.domain.popupstore.entity.PopupStoreAttribute;
import com.sparta.popupstore.domain.popupstore.entity.PopupStoreAttributes;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class PopupStoreAttributeResponseDto {
    @Schema(description = "속성 타입")
    private final PopupStoreAttribute attribute;
    @Schema(description = "허용 여부")
    private final Boolean isAllow;

    public PopupStoreAttributeResponseDto(PopupStoreAttributes popupStoreAttributes) {
        this.attribute = popupStoreAttributes.getAttribute();
        this.isAllow = popupStoreAttributes.getIsAllow();
    }
}