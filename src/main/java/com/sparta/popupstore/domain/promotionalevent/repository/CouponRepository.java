package com.sparta.popupstore.domain.promotionalevent.repository;

import com.sparta.popupstore.domain.promotionalevent.entity.Coupon;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    List<Coupon> findByUserId(Long userId);
}
