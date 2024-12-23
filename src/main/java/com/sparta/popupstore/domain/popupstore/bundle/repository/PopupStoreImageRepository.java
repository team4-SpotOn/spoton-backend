package com.sparta.popupstore.domain.popupstore.bundle.repository;

import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.bundle.entity.PopupStoreImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PopupStoreImageRepository extends JpaRepository<PopupStoreImage, Long> {
    List<PopupStoreImage> findAllByPopupStore(PopupStore popupStore);

    void deleteAllByPopupStore(PopupStore popupStore);
}
