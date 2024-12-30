package com.sparta.popupstore.domain.promotionevent.repository;

import com.sparta.popupstore.domain.promotionevent.entity.PromotionEvent;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PromotionEventRepository extends JpaRepository<PromotionEvent, Long> {
//    @Modifying
//    @Query("update PromotionEvent p set p.deletedAt = now() where p.id = :promotionEventId")
//    void deletePromotionEvent(@Param("promotionEventId") Long promotionEventId);

//    @Query(value = "select * from events e where timestampdiff(month , e.end_date_time, now()) >= 6", nativeQuery = true)
//    List<PromotionEvent> findAllByEndDateTimeAfterSixMonths();

    // 추후 스케줄러에 사용될 수 있음.
    @Modifying
    @Query("update PromotionEvent p set p.couponGetCount = p.couponGetCount + 1 where p.id = :promotionEventId")
    void couponGetCountUp(@Param("promotionEventId") Long promotionEventId);

    // 프로모션 이벤트 쿠폰 발급 동시성 제어 비관적 락 사용
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from PromotionEvent p where p.id = :promotionEventId")
    PromotionEvent findByIdWithPessimisticLock(Long promotionEventId);

    List<PromotionEvent> findAllByEndDateTimeBefore(LocalDateTime sixMonthBefore);

    Optional<PromotionEvent> findByIdAndStartDateTimeAfterOrEndDateTimeBefore(Long id, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
