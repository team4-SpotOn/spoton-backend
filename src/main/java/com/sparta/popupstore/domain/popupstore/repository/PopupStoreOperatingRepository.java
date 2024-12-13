package com.sparta.popupstore.domain.popupstore.repository;

import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.entity.PopupStoreOperating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PopupStoreOperatingRepository extends JpaRepository<PopupStoreOperating, Long> {
    List<PopupStoreOperating> findByPopupStore(PopupStore popupStore);

    void deleteByPopupStore(PopupStore popupStore);
}
