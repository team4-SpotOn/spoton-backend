package com.sparta.popupstore.domain.promotionevent.repository;

import com.sparta.popupstore.domain.promotionevent.entity.PromotionEvent;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface PromotionEventRepository extends JpaRepository<PromotionEvent, Long> {
    // 프로모션 이벤트 쿠폰 발급 동시성 제어 비관적 락 사용
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from PromotionEvent p where p.id = :promotionEventId")
    PromotionEvent findByIdWithPessimisticLock(Long promotionEventId);

    List<PromotionEvent> findAllByEndDateTimeBefore(LocalDateTime sixMonthBefore);
}
