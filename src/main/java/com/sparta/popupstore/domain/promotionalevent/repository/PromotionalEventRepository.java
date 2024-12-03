package com.sparta.popupstore.domain.promotionalevent.repository;

import com.sparta.popupstore.domain.promotionalevent.entity.PromotionalEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionalEventRepository extends JpaRepository<PromotionalEvent, Long> {
}
