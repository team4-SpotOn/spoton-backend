package com.sparta.popupstore.domain.popupstore.repository;

import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PopupStoreRepository extends JpaRepository<PopupStore, Long> {

    List<PopupStore> findAllByCompanyId(Long companyId);
}
