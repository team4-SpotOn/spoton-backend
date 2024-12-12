package com.sparta.popupstore.domain.popupstore.service;

import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.entity.PopupStoreOperating;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PopupStoreOperatingService {

    // 운영 시간 처리 로직
    public PopupStoreOperating createOperatingHours(PopupStore popupStore, DayOfWeek dayOfWeek, Map<DayOfWeek, LocalTime> startTimes, Map<DayOfWeek, LocalTime> endTimes) {
        LocalTime startTime = startTimes.get(dayOfWeek);
        LocalTime endTime = endTimes.get(dayOfWeek);

        if (startTime == null && endTime == null) {
            return null;
        }

        if (startTime == null || endTime == null) {
            throw new CustomApiException(ErrorCode.POPUP_STORE_OPERATING_BAD_REQUEST);
        }

        return PopupStoreOperating.builder()
                .popupStore(popupStore)
                .dayOfWeek(dayOfWeek)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }
}
