package com.sparta.popupstore.domain.promotionevent.dto.response;

import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class PromotionEventFindOnePopupStoreResponseDto {
    private final Long id;
    private final String name;
    private final String image;
    private final int price;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final LocalTime startTime;
    private final LocalTime endTime;

    public PromotionEventFindOnePopupStoreResponseDto(PopupStore popupStore) {
        this.id = popupStore.getId();
        this.name = popupStore.getName();
        this.image = popupStore.getImage();
        this.price = popupStore.getPrice();
        this.startDate = popupStore.getStartDate();
        this.endDate = popupStore.getEndDate();
        this.startTime = popupStore.getStartTime();
        this.endTime = popupStore.getEndTime();
    }
}
