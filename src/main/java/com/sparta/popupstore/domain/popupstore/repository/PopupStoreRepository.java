package com.sparta.popupstore.domain.popupstore.repository;

import com.sparta.popupstore.domain.company.entity.Company;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PopupStoreRepository extends JpaRepository<PopupStore, Long>, PopupStoreQueryDsl {
    List<PopupStore> findAllByCompany(Company company);

    Optional<PopupStore> findByIdAndEndDateAfter(Long popupStoreId, LocalDate endDate);

    boolean existsByCompanyAndEndDateGreaterThanEqual(Company company, LocalDate now);
}
