package com.sparta.popupstore.domain.popupstore.bundle.repository;

import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.bundle.entity.PopupStoreOperating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

public interface PopupStoreOperatingRepository extends JpaRepository<PopupStoreOperating, Long> {
    List<PopupStoreOperating> findAllByPopupStore(PopupStore popupStore);

    Optional<PopupStoreOperating> findByPopupStoreAndDayOfWeek(PopupStore popupStore, DayOfWeek dayOfWeek);

    void deleteAllByPopupStore(PopupStore popupStore);
}
