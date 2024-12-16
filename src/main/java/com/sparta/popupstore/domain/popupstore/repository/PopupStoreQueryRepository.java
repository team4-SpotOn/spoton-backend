package com.sparta.popupstore.domain.popupstore.repository;

import com.sparta.popupstore.domain.popupstore.entity.PopupStore;

import java.time.LocalDate;
import java.util.List;

public interface PopupStoreQueryRepository {
   public List<PopupStore> findByStartDateAndEndDate(LocalDate startDate, LocalDate endDate);
}
