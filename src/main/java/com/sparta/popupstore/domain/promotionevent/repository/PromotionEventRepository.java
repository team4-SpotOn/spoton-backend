package com.sparta.popupstore.domain.promotionevent.repository;

import com.sparta.popupstore.domain.promotionevent.entity.PromotionEvent;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PromotionEventRepository extends JpaRepository<PromotionEvent, Long> {
    @Query("select p from PromotionEvent p left join fetch p.popupStore")
    Page<PromotionEvent> findAllPromotionalEvents(Pageable pageable);

    @Modifying
    @Query("update PromotionEvent p set p.deletedAt = now() where p.id = :promotionEventId")
    void deletePromotionEvent(@Param("promotionEventId") Long promotionEventId);

    @Query(value = "select * from events e where timestampdiff(month , e.end_date_time, now()) >= 6", nativeQuery = true)
    List<PromotionEvent> findAllByEndDateTimeAfterSixMonths();

    // 추후 스케줄러에 사용될 수 있음.
    @Modifying
    @Query("update PromotionEvent p set p.couponGetCount = p.couponGetCount + 1 where p.id = :promotionEventId")
    void couponGetCountUp(@Param("promotionEventId") Long promotionEventId);

    // 프로모션 이벤트 쿠폰 발급 동시성 제어 비관적 락 사용
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from PromotionEvent p where p.id = :promotionEventId")
    PromotionEvent findByIdWithPessimisticLock(Long promotionEventId);

}
