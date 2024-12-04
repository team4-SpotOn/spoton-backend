package com.sparta.popupstore.domain.popupstore.dto.request;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class PopupStoreCreateRequestDto {
    private String name;
    private String content;
    private String imageUrl;
    private String price;
    private String address;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;

}