package com.sparta.popupstore.domain.promotionevent.dto.response;

import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PromotionEventFindOnePopupStoreResponseDto {
    private final Long id;
    private final String name;
    private final int price;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public PromotionEventFindOnePopupStoreResponseDto(PopupStore popupStore) {
        this.id = popupStore.getId();
        this.name = popupStore.getName();
        this.price = popupStore.getPrice();
        this.startDate = popupStore.getStartDate();
        this.endDate = popupStore.getEndDate();
    }
}
