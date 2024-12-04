package com.sparta.popupstore.domain.popupstore.dto.response;

import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PopupStoreUpdateResponseDto {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private int price;
    private String content;
    private String imageUrl;
    private String address;

    public PopupStoreUpdateResponseDto(PopupStore popupStore) {
        this.name = popupStore.getName();
        this.startDate = popupStore.getStartDate();
        this.endDate = popupStore.getEndDate();
        this.price = popupStore.getPrice();
        this.content = popupStore.getContents();
        this.imageUrl = popupStore.getImage();
        this.address = popupStore.getAddress();
    }
}
