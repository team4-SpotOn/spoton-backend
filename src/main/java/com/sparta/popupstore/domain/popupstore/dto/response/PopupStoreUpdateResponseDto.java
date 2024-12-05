package com.sparta.popupstore.domain.popupstore.dto.response;

import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class PopupStoreUpdateResponseDto {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private int price;
    private String content;
    private String imagePath;
    private String address;

    public PopupStoreUpdateResponseDto(PopupStore popupStore) {
        this.name = popupStore.getName();
        this.startDate = popupStore.getStartDate();
        this.endDate = popupStore.getEndDate();
        this.startTime = popupStore.getStartTime();
        this.endTime = popupStore.getEndTime();
        this.price = popupStore.getPrice();
        this.content = popupStore.getContents();
        this.imagePath = popupStore.getImage();
        this.address = popupStore.getAddress();
    }
}
