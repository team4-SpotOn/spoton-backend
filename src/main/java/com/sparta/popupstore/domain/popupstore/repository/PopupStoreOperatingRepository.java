package com.sparta.popupstore.domain.popupstore.repository;

import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.entity.PopupStoreOperating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PopupStoreOperatingRepository extends JpaRepository<PopupStoreOperating, Long> {
    void deleteByPopupStore(PopupStore popupStore);
}
