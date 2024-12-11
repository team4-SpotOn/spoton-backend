package com.sparta.popupstore.domain.popupstore.repository;

import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.entity.PopupStoreOperating;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PopupStoreOperatingRepository extends JpaRepository<PopupStoreOperating, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM PopupStoreOperating po WHERE po.popupStore = :popupStore")
    void deleteByPopupStore(PopupStore popupStore);
}
