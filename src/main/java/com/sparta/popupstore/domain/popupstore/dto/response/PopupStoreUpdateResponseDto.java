package com.sparta.popupstore.domain.popupstore.dto.response;

import com.sparta.popupstore.domain.common.entity.Address;
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
    private Address address;

    public PopupStoreUpdateResponseDto(PopupStore popupStore) {
        this.name = popupStore.getName();
        this.startDate = popupStore.getStartDate();
        this.endDate = popupStore.getEndDate();
        this.price = popupStore.getPrice();
        this.content = popupStore.getContents();
        this.address = popupStore.getAddress();
    }
}
