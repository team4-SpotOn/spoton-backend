package com.sparta.popupstore.domain.promotionevent.controller;

import com.sparta.popupstore.domain.common.annotation.AuthUser;
import com.sparta.popupstore.domain.promotionevent.dto.response.CouponCreateResponseDto;
import com.sparta.popupstore.domain.promotionevent.dto.response.PromotionEventFindAllResponseDto;
import com.sparta.popupstore.domain.promotionevent.service.PromotionEventService;
import com.sparta.popupstore.domain.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "PromotionEvents", description = "프로모션 이벤트 관련 API")
@RequiredArgsConstructor
@RequestMapping("/promotionEvents")
public class PromotionEventController {

    private final PromotionEventService promotionEventService;

    @Operation(summary = "프로모션 이벤트 다건 조회", description = "현재 등록되어 있는 이벤트들을 보여줍니다. pageNum 과 pageSize 는 default 값이 각각 1과 10입니다.")
    @Parameter(name = "pageNum", description = "현재 페이지 번호")
    @Parameter(name = "pageSize", description = "한 페이지에서 볼 이벤트 갯수")
    @GetMapping
    public ResponseEntity<Page<PromotionEventFindAllResponseDto>> findAllPromotionalEvents(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(promotionEventService.findAllPromotionalEvents(page, size));
    }

    @Operation(summary = "프로모션 이벤트 쿠폰 신청 및 발급")
    @PostMapping("/{promotionEventId}/coupons")
    public ResponseEntity<CouponCreateResponseDto> couponApplyAndIssuance(
            @PathVariable(name = "promotionEventId") Long promotionEventId,
            @AuthUser User user
    ) {
        return ResponseEntity.ok(promotionEventService.couponApplyAndIssuance(promotionEventId, user));
    }
}
