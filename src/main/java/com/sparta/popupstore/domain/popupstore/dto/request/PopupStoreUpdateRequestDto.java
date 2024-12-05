package com.sparta.popupstore.domain.popupstore.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class PopupStoreUpdateRequestDto {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    @Min(value = 0, message = "가격은 0이상이어야만 합니다.")
    private String price;
    private String content;
    private String imagePath;
    private String address;
}
