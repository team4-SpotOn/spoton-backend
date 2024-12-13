package com.sparta.popupstore.domain.popupstore.dto.request;

import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.entity.PopupStoreOperating;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
public class PopupStoreOperatingRequestDto {
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    public PopupStoreOperating toEntity(PopupStore popupStore) {
        return PopupStoreOperating.builder()
                .popupStore(popupStore)
                .dayOfWeek(this.dayOfWeek)
                .startTime(this.startTime)
                .endTime(this.endTime)
                .build();
    }
}
