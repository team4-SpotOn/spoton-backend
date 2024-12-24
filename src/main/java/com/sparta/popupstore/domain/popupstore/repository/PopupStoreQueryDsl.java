package com.sparta.popupstore.domain.popupstore.repository;

import com.sparta.popupstore.domain.popupstore.enums.PopupStoreStatus;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

import java.util.List;

public interface PopupStoreQueryDsl{
    Page<PopupStore> findByStatus(Pageable pageable, PopupStoreStatus popupStoreStatus);
    List<PopupStore> findByStartDateAndEndDate(LocalDate startDate, LocalDate endDate, Long page, Long size);
    List<PopupStore> findByMonth(Long page, Long size);
}
