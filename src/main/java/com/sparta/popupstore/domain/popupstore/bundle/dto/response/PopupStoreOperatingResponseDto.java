package com.sparta.popupstore.domain.popupstore.bundle.dto.response;

import com.sparta.popupstore.domain.popupstore.bundle.entity.PopupStoreOperating;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
public class PopupStoreOperatingResponseDto {
    private final DayOfWeek dayOfWeek;
    private final LocalTime startTime;
    private final LocalTime endTime;

    public PopupStoreOperatingResponseDto(PopupStoreOperating operating) {
        this.dayOfWeek = operating.getDayOfWeek();
        this.startTime = operating.getStartTime();
        this.endTime = operating.getEndTime();
    }
}
