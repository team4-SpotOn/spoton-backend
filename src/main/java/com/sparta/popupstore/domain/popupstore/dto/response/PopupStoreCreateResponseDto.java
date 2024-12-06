package com.sparta.popupstore.domain.popupstore.dto.response;

import com.sparta.popupstore.domain.common.entity.Address;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PopupStoreCreateResponseDto {
    private String name;
    private String content;
    private int price;
    private Address address;
    private LocalDate startDate;
    private LocalDate endDate;

    public PopupStoreCreateResponseDto(PopupStore popupStore) {
        this.name = popupStore.getName();
        this.content = popupStore.getContents();
        this.price = popupStore.getPrice();
        this.address = popupStore.getAddress();
        this.startDate = popupStore.getStartDate();
        this.endDate = popupStore.getEndDate();
    }

}
