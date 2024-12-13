package com.sparta.popupstore.domain.popupstore.repository;

import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.entity.PopupStoreAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PopupStoreAttributeRepository extends JpaRepository<PopupStoreAttribute, Long> {
    void deleteByPopupStore(PopupStore popupStore);

    List<PopupStoreAttribute> findByPopupStore(PopupStore popupStore);
}
