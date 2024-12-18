package com.sparta.popupstore.domain.admin.controller;

import com.sparta.popupstore.domain.admin.service.AdminService;
import com.sparta.popupstore.domain.common.annotation.CheckAdmin;
import com.sparta.popupstore.domain.popupstore.dto.request.PopupStoreUpdateRequestDto;
import com.sparta.popupstore.domain.popupstore.dto.response.PopupStoreUpdateResponseDto;
import com.sparta.popupstore.domain.popupstore.service.PopupStoreService;
import com.sparta.popupstore.domain.promotionevent.dto.request.PromotionEventCreateRequestDto;
import com.sparta.popupstore.domain.promotionevent.dto.request.PromotionEventUpdateRequestDto;
import com.sparta.popupstore.domain.promotionevent.dto.response.PromotionEventCreateResponseDto;
import com.sparta.popupstore.domain.promotionevent.dto.response.PromotionEventFindOneResponseDto;
import com.sparta.popupstore.domain.promotionevent.dto.response.PromotionEventUpdateResponseDto;
import com.sparta.popupstore.domain.promotionevent.service.PromotionEventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admins")
public class AdminController {

    private final AdminService adminService;
    private final PopupStoreService popupStoreService;
    private final PromotionEventService promotionEventService;

    @Operation(summary = "관리자 - 팝업 스토어 수정")
    @Parameter(name = "name", description = "수정할 팝업스토어 명")
    @Parameter(name = "content", description = "수정할 팝업스토어 내용")
    @Parameter(name = "price", description = "수정할 팝업스토어 가격")
    @Parameter(name = "address", description = "수정할 팝업스토어 주소")
    @Parameter(name = "startDate", description = "수정할 팝업스토어 시작일")
    @Parameter(name = "endDate", description = "수정할 팝업스토어 종료일")
    @Parameter(name = "startTime", description = "수정할 팝업스토어 개장시간")
    @Parameter(name = "endTime", description = "수정할 팝업스토어 폐장시간")
    @Parameter(name = "images", description = "수정할 이미지 명과 이미지 순서가 들어있는 List Dto")
    @CheckAdmin
    @PatchMapping("/popupstores/{popupStoreId}")
    public ResponseEntity<PopupStoreUpdateResponseDto> adminUpdatePopupStore(
            @PathVariable Long popupStoreId,
            @RequestBody @Valid PopupStoreUpdateRequestDto requestDto
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(popupStoreService.updatePopupStore(popupStoreId, requestDto));
    }

    @Operation(summary = "관리자 - 팝업스토어 삭제", description = "popupStoreId에 해당하는 팝업스토어를 삭제합니다.")
    @Parameter(name = "user", description = "로그인한 관리자")
    @Parameter(name = "popupStoreId", description = "팝업 스토어 고유번호")
    @CheckAdmin
    @DeleteMapping("/popupstores/{popupStoreId}")
    public ResponseEntity<Void> adminDeletePopupStore(
            @PathVariable("popupStoreId") Long popupStoreId
    ) {
        popupStoreService.deletePopupStore(popupStoreId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

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
    @PostMapping("/promotionEvents")
    public ResponseEntity<PromotionEventCreateResponseDto> createEvent(
            @Valid @RequestBody PromotionEventCreateRequestDto createRequestDto,
            @RequestParam(required = false, name = "popupStoreId") Long popupStoreId
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(promotionEventService.createEvent(createRequestDto, popupStoreId));
    }

    @Operation(summary = "프로모션 이벤트 단건 조회")
    @GetMapping("/promotionEvents/{promotionEventId}")
    public ResponseEntity<PromotionEventFindOneResponseDto> findOnePromotionalEvent(
            @PathVariable(name = "promotionEventId") Long promotionEventId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(promotionEventService.findOnePromotionEvent(promotionEventId));
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
    @PatchMapping("/promotionEvents/{promotionEventId}")
    public ResponseEntity<PromotionEventUpdateResponseDto> updateEvent(
            @Valid @RequestBody PromotionEventUpdateRequestDto updateRequestDto,
            @PathVariable(name = "promotionEventId") Long promotionEventId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(promotionEventService.updatePromotionEvent(updateRequestDto, promotionEventId));
    }

    @Operation(summary = "프로모션 이벤트 삭제")
    @CheckAdmin
    @DeleteMapping("/promotionEvents/{promotionEventId}")
    public ResponseEntity<Void> deleteEvent(
            @PathVariable(name = "promotionEventId") Long promotionEventId
    ) {
        promotionEventService.deletePromotionEvent(promotionEventId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
