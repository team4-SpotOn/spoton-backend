package com.sparta.popupstore.domain.popupstore.repository;

import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PopupStoreRepository extends JpaRepository<PopupStore, Long> {

    List<PopupStore> findByCompanyId(Long companyId);
}
