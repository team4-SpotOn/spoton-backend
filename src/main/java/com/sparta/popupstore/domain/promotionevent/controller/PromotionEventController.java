package com.sparta.popupstore.domain.promotionevent.controller;

import com.sparta.popupstore.domain.common.annotation.AuthUser;
import com.sparta.popupstore.domain.common.annotation.CheckAdmin;
import com.sparta.popupstore.domain.promotionevent.dto.request.PromotionEventCreateRequestDto;
import com.sparta.popupstore.domain.promotionevent.dto.request.PromotionEventUpdateRequestDto;
import com.sparta.popupstore.domain.promotionevent.dto.response.PromotionEventCreateResponseDto;
import com.sparta.popupstore.domain.promotionevent.dto.response.PromotionEventFindAllResponseDto;
import com.sparta.popupstore.domain.promotionevent.dto.response.PromotionEventFindOneResponseDto;
import com.sparta.popupstore.domain.promotionevent.dto.response.PromotionEventUpdateResponseDto;
import com.sparta.popupstore.domain.promotionevent.dto.response.CouponCreateResponseDto;
import com.sparta.popupstore.domain.promotionevent.service.PromotionEventService;
import com.sparta.popupstore.domain.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "PromotionEvents", description = "프로모션 이벤트 관련 API")
@RequiredArgsConstructor
public class PromotionEventController {

    private final PromotionEventService promotionEventService;

    @Operation(summary = "프로모션 이벤트 추가")
    @Parameter(name = "title", description = "이벤트 제목")
    @Parameter(name = "description", description = "이벤트 설명")
    @Parameter(name = "discountPercentage", description = "할인 율")
    @Parameter(name = "totalCount", description = "총 쿠폰의 갯수")
    @Parameter(name = "couponExpirationPeriod", description = "쿠폰 만료 기간")
    @Parameter(name = "startTime", description = "시작일")
    @Parameter(name = "endTime", description = "종료일")
    @Parameter(name = "imageUrl", description = "이미지 저장된 경로")
    @Parameter(name = "popupStoreId", description = "팝업 스토어 고유번호 / 만약 전체를 대상으로 진행하는 이벤트 일 시 팝업스토어 고유번호는 생략")
    @CheckAdmin
    @PostMapping("/admin/promotionEvents")
    public ResponseEntity<PromotionEventCreateResponseDto> createEvent(
            @Valid @RequestBody PromotionEventCreateRequestDto promotionEventCreateRequestDto,
            @RequestParam(required = false, name = "popupStoreId") Long popupStoreId
            ){
        return ResponseEntity.status(HttpStatus.CREATED).body(promotionEventService.createEvent(promotionEventCreateRequestDto, popupStoreId));
    }

    @Operation(summary = "프로모션 이벤트 다건 조회", description = "현재 등록되어 있는 이벤트들을 보여줍니다. pageNum 과 pageSize 는 default 값이 각각 1과 10입니다.")
    @Parameter(name = "pageNum", description = "현재 페이지 번호")
    @Parameter(name = "pageSize", description = "한 페이지에서 볼 이벤트 갯수")
    @GetMapping("/promotionEvents")
    public ResponseEntity<Page<PromotionEventFindAllResponseDto>> findAllPromotionalEvents(
            @RequestParam(name = "page",required = false, defaultValue = "1") int page,
            @RequestParam(name = "size",required = false, defaultValue = "10") int size
    ){
        return ResponseEntity.ok(promotionEventService.findAllPromotionalEvents(page, size));
    }

    @Operation(summary = "프로모션 이벤트 단건 조회")
    @GetMapping("/promotionEvents/{promotionEventId}")
    public ResponseEntity<PromotionEventFindOneResponseDto> findOnePromotionalEvent(
            @PathVariable(name = "promotionEventId") Long promotionEventId
    ){
        return ResponseEntity.ok(promotionEventService.findOnePromotionEvent(promotionEventId));
    }

    @Operation(summary = "프로모션 이벤트 수정")
    @Parameter(name = "title", description = "이벤트 제목")
    @Parameter(name = "description", description = "이벤트 설명")
    @Parameter(name = "discountPercentage", description = "할인 율")
    @Parameter(name = "totalCount", description = "총 쿠폰의 갯수")
    @Parameter(name = "couponExpirationPeriod", description = "쿠폰 만료 기간")
    @Parameter(name = "startTime", description = "시작일")
    @Parameter(name = "endTime", description = "종료일")
    @Parameter(name = "imageUrl", description = "이미지 저장된 경로")
    @CheckAdmin
    @PatchMapping("/admin/promotionEvents/{promotionEventId}")
    public ResponseEntity<PromotionEventUpdateResponseDto> updateEvent(
            @Valid @RequestBody PromotionEventUpdateRequestDto promotionEventUpdateRequestDto,
            @PathVariable(name = "promotionEventId") Long promotionEventId
    ){
        return ResponseEntity.ok(promotionEventService.updatePromotionEvent(promotionEventUpdateRequestDto, promotionEventId));
    }

    @Operation(summary = "프로모션 이벤트 삭제")
    @CheckAdmin
    @DeleteMapping("/admin/promotionEvents/{promotionEventId}")
    public ResponseEntity<Void> deleteEvent(
            @PathVariable(name = "promotionEventId") Long promotionEventId
    ){
        promotionEventService.deletePromotionEvent(promotionEventId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "프로모션 이벤트 쿠폰 신청 및 발급")
    @PostMapping("/promotionEvents/{promotionEventId}/coupons")
    public ResponseEntity<CouponCreateResponseDto> couponApplyAndIssuance(
            @PathVariable(name = "promotionEventId") Long promotionEventId,
            @AuthUser User user
    ){
        return ResponseEntity.ok(promotionEventService.couponApplyAndIssuance(promotionEventId, user));
    }
}
