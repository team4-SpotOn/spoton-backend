package com.sparta.popupstore.domain.promotionevent.repository;

import com.sparta.popupstore.domain.promotionevent.entity.PromotionEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PromotionEventRepository extends JpaRepository<PromotionEvent, Long> {
    @Query("select p from PromotionEvent p left join fetch p.popupStore")
    Page<PromotionEvent> findAllPromotionalEvents(Pageable pageable);
}
