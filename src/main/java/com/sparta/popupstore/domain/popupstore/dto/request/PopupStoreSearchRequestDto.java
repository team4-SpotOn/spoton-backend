package com.sparta.popupstore.domain.popupstore.dto.request;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PopupStoreSearchRequestDto {
    private LocalDate startDate;
    private LocalDate endDate;
}
