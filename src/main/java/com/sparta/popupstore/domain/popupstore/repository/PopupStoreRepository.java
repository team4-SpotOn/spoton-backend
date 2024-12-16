package com.sparta.popupstore.domain.popupstore.repository;

import com.sparta.popupstore.domain.popupstore.entity.PopupStore;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PopupStoreRepository extends JpaRepository<PopupStore, Long>, PopupStoreQueryDsl {
    List<PopupStore> findAllByCompanyId(Long companyId);
    Optional<PopupStore> findByIdAndEndDateAfter(Long popupStoreId, LocalDate endDate);
}
