package com.sparta.popupstore.domain.popupstore.bundle.repository;

import com.sparta.popupstore.domain.popupstore.bundle.enums.PopupStoreAttributeEnum;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.bundle.entity.PopupStoreAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PopupStoreAttributeRepository extends JpaRepository<PopupStoreAttribute, Long> {
    List<PopupStoreAttribute> findAllByPopupStore(PopupStore popupStore);

    void deleteAllByPopupStore(PopupStore popupStore);

    Optional<PopupStoreAttribute> findByPopupStoreAndAttribute(PopupStore popupStore, PopupStoreAttributeEnum attribute);
}
