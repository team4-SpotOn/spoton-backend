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
    default PromotionEvent findByPromotionEventId(Long promotionEventId) {
        return findOnePromotionEvent(promotionEventId)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않거나 이미 삭제된 이벤트입니다."));
    }

    @Query("select p from PromotionEvent p left join fetch p.popupStore where p.deletedAt is null")
    Page<PromotionEvent> findAllPromotionalEvents(Pageable pageable);

    @Modifying
    @Query("update PromotionEvent p set p.deletedAt = now() where p.id = :promotionEventId")
    void deletePromotionEvent(@Param("promotionEventId") Long promotionEventId);

    @Query("select p from PromotionEvent p where p.id = :promotionEventId and p.deletedAt is null")
    Optional<PromotionEvent> findOnePromotionEvent(@Param("promotionEventId") Long promotionEventId);
}
