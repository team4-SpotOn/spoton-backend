package com.sparta.popupstore.domain.popupstore.dto.response;

import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class PopupStoreCreateResponseDto {
    private String name;
    private String content;
    private String imageUrl;
    private int price;
    private String address;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;

    public PopupStoreCreateResponseDto(PopupStore popupStore) {
        this.name = popupStore.getName();
        this.content = popupStore.getContents();
        this.imageUrl = popupStore.getImage();
        this.price = popupStore.getPrice();
        this.address = popupStore.getAddress();
        this.startDate = popupStore.getStartDate();
        this.endDate = popupStore.getEndDate();
        this.startTime = popupStore.getStartTime();
        this.endTime = popupStore.getEndTime();
    }

}
