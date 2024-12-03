package com.sparta.popupstore.domain.promotionalevent.repository;

import com.sparta.popupstore.domain.promotionalevent.entity.PromotionalEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PromotionalEventRepository extends JpaRepository<PromotionalEvent, Long> {
    @Query(value = "select p from PromotionalEvent p left join fetch p.popupStore")
    Page<PromotionalEvent> findAllPromotionalEvents(Pageable pageable);
}
