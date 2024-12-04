package com.sparta.popupstore.domain.promotionevent.repository;


import com.sparta.popupstore.domain.promotionevent.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    List<Coupon> findByUserId(Long userId);

    @Query("select exists (select 1 from Coupon c where c.promotionEvent.id = :promotionEventId and c.user.id = :userId)")
    boolean existsByUserIdAndPromotionEventId(
            @Param("promotionEventId") Long promotionEventId,
            @Param("userId") Long userId);
}