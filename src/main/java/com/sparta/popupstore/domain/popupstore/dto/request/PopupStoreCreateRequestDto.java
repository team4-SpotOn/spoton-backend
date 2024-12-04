package com.sparta.popupstore.domain.popupstore.dto.request;

import com.sparta.popupstore.domain.company.entity.Company;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class PopupStoreCreateRequestDto {
    private String name;
    private String content;
    private int price;
    private String address;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;

    public PopupStore toEntity(Company company, String imageUrl) {
        return PopupStore.builder()
                .company(company)
                .name(this.name)
                .contents(this.content)
                .image(imageUrl)
                .price(this.price)
                .address(this.address)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .startTime(this.startTime)
                .endTime(this.endTime)
                .build();
    }
}
