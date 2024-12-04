package com.sparta.popupstore.domain.promotionevent.dto.response;

import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import lombok.Getter;

@Getter
public class PromotionEventFindPopupStoreResponseDto {
    private final Long id;
    private final String name;
    private final String image;
    private final int price;

    public PromotionEventFindPopupStoreResponseDto(PopupStore popupStore) {
        this.id = popupStore.getId();
        this.name = popupStore.getName();
        this.image = popupStore.getImage();
        this.price = popupStore.getPrice();
    }
}
