package com.sparta.popupstore.domain.popupstore.repository;

import com.sparta.popupstore.domain.popupstore.dto.response.PopupStoreSearchResponseDto;
import com.sparta.popupstore.domain.popupstore.enums.PopupStoreStatus;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

import java.util.List;

public interface PopupStoreQueryDsl{
    PopupStoreSearchResponseDto findByStatus(Pageable pageable, PopupStoreStatus popupStoreStatus);
    List<PopupStore> findByStartDateAndEndDate(LocalDate startDate, LocalDate endDate, Long page, Long size);
}
