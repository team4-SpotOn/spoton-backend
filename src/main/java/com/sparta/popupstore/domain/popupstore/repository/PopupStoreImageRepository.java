package com.sparta.popupstore.domain.popupstore.repository;

import com.sparta.popupstore.domain.popupstore.entity.PopupStoreImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PopupStoreImageRepository extends JpaRepository<PopupStoreImage, Long> {

    List<PopupStoreImage> findAllByPopupStoreIdOrderBySort(Long popupStoreId);
}
