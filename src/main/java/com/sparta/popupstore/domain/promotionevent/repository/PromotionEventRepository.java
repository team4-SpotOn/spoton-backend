package com.sparta.popupstore.domain.promotionevent.repository;

import com.sparta.popupstore.domain.promotionevent.entity.PromotionEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PromotionEventRepository extends JpaRepository<PromotionEvent, Long> {
    @Query("select p from PromotionEvent p left join fetch p.popupStore where p.deletedAt is null")
    Page<PromotionEvent> findAllPromotionalEvents(Pageable pageable);

    @Modifying
    @Query("update PromotionEvent p set p.deletedAt = now() where p.id = :promotionEventId")
    void deletePromotionEvent(@Param("promotionEventId") Long promotionEventId);

    @Query("select p from PromotionEvent p where p.id = :promotionEventId and p.deletedAt is null")
    Optional<PromotionEvent> findByPromotionEventId(@Param("promotionEventId") Long promotionEventId);
}
