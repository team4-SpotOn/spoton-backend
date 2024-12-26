package com.sparta.popupstore.domain.coupon.controller;

import com.sparta.popupstore.domain.common.annotation.AuthUser;
import com.sparta.popupstore.domain.coupon.dto.response.CouponCreateResponseDto;
import com.sparta.popupstore.domain.coupon.service.CouponService;
import com.sparta.popupstore.domain.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "쿠폰 api", description = "쿠폰 발급 api")
@RequestMapping("/coupons")
public class CouponController {

    private final CouponService couponService;

    @Operation(summary = "프로모션 이벤트 쿠폰 신청 및 발급")
    @Parameter(name = "promotionEventId", description = "쿠폰을 발급할 프로모션 이벤트의 기본키")
    @Parameter(name = "user", description = "로그인한 유저")
    @PostMapping("/{promotionEventId}/coupons")
    public ResponseEntity<CouponCreateResponseDto> couponApplyAndIssuance(
            @AuthUser User user,
            @PathVariable(name = "promotionEventId") Long promotionEventId
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(couponService.couponApplyAndIssuance(user, promotionEventId));
    }

}
