package com.sparta.popupstore.domain.popupstore.repository;

import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.entity.PopupStoreAttributes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PopupStoreAttributeRepository extends JpaRepository<PopupStoreAttributes, Long> {
    void deleteByPopupStoreId(Long id);
    List<PopupStoreAttributes> findByPopupStoreId(Long popupStoreId);
}
