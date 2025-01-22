package com.sparta.popupstore.domain.popupstore.bundle.dto.request;

import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.bundle.entity.PopupStoreOperating;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
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
