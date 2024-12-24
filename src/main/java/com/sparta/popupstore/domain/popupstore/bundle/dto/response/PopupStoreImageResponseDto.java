package com.sparta.popupstore.domain.popupstore.bundle.dto.response;

import com.sparta.popupstore.domain.popupstore.bundle.entity.PopupStoreImage;
import lombok.Getter;

@Getter
public class PopupStoreImageResponseDto {
    private final Long id;
    private final String imageUrl;
    private final Integer sort;

    public PopupStoreImageResponseDto(PopupStoreImage popupStoreImage) {
        this.id = popupStoreImage.getId();
        this.imageUrl = popupStoreImage.getImageUrl();
        this.sort = popupStoreImage.getSort();
    }
}
