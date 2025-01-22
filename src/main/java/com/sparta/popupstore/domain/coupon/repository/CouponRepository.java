package com.sparta.popupstore.domain.coupon.repository;

import com.sparta.popupstore.domain.coupon.entity.Coupon;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    List<Coupon> findAllByUserId(Long userId);

    boolean existsByPromotionEventIdAndUserId(Long promotionEventId, Long userId);

    @Modifying
    @Query("update Coupon c set c.couponStatus = 'EXPIRATION' where c.couponExpirationPeriod < now() and c.couponStatus = 'ISSUED'")
    void softDeleteCouponByExpiration();

    // 쿠폰 개수 구하기
    Long countByPromotionEventId(Long promotionEventId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Coupon c WHERE c.promotionEventId = :promotionEventId AND c.userId = :userId")
    Coupon findByIdWithPessimisticLock(@Param("promotionEventId") Long promotionEventId, @Param("userId") Long userId);

    Optional<Coupon> findBySerialNumber(String couponSerialNumber);
}
