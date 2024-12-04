package com.sparta.popupstore.domain.promotionevent.repository;


import com.sparta.popupstore.domain.promotionevent.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    List<Coupon> findByUserId(Long userId);
}