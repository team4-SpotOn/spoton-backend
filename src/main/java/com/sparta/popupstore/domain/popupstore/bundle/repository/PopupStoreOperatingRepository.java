package com.sparta.popupstore.domain.popupstore.bundle.repository;

import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.bundle.entity.PopupStoreOperating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PopupStoreOperatingRepository extends JpaRepository<PopupStoreOperating, Long> {
    List<PopupStoreOperating> findAllByPopupStore(PopupStore popupStore);

    void deleteAllByPopupStore(PopupStore popupStore);
}
