package com.sparta.popupstore.domain.promotionalevent.controller;

import com.sparta.popupstore.domain.common.annotation.AuthUser;
import com.sparta.popupstore.domain.promotionalevent.dto.request.PromotionalEventCreateRequestDto;
import com.sparta.popupstore.domain.promotionalevent.dto.response.PromotionEventResponseDto;
import com.sparta.popupstore.domain.promotionalevent.dto.response.PromotionalEventCreateResponseDto;
import com.sparta.popupstore.domain.promotionalevent.service.PromotionalEventService;
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
@Tag(name = "PromotionalEvents", description = "이벤트 관련 API")
@RequestMapping("/promotionalEvents")
@RequiredArgsConstructor
public class PromotionalEventController {

    private final PromotionalEventService promotionalEventService;

    @Operation(summary = "이벤트 추가")
    @Parameter(name = "title", description = "이벤트 제목")
    @Parameter(name = "description", description = "이벤트 설명")
    @Parameter(name = "discountPercentage", description = "할인 율")
    @Parameter(name = "totalCount", description = "총 쿠폰의 갯수")
    @Parameter(name = "startTime", description = "시작일")
    @Parameter(name = "endTime", description = "종료일")
    @Parameter(name = "popupStoreId", description = "팝업 스토어 고유번호 / 만약 전체를 대상으로 진행하는 이벤트 일 시 팝업스토어 고유번호는 생략")
    @PostMapping("/admin")
    public ResponseEntity<PromotionalEventCreateResponseDto> createEvent(
            @AuthUser User user,
            @Valid @RequestBody PromotionalEventCreateRequestDto promotionalEventCreateRequestDto,
            @RequestParam(required = false, name = "popupStoreId") Long popupStoreId
            ){
        return ResponseEntity.status(HttpStatus.CREATED).body(promotionalEventService.createEvent(promotionalEventCreateRequestDto, popupStoreId));
    }

    @Operation(summary = "이벤트 다건 조회", description = "현재 등록되어 있는 이벤트들을 보여줍니다. pageNum 과 pageSize 는 default 값이 각각 1과 10입니다.")
    @Parameter(name = "pageNum", description = "현재 페이지 번호")
    @Parameter(name = "pageSize", description = "한 페이지에서 볼 이벤트 갯수")
    @GetMapping
    public ResponseEntity<Page<PromotionEventResponseDto>> findAllPromotionalEvents(
            @RequestParam(name = "pageNum",required = false, defaultValue = "1") int pageNum,
            @RequestParam(name = "pageSize",required = false, defaultValue = "10") int pageSize
    ){
        return ResponseEntity.ok(promotionalEventService.findAllPromotionalEvents(pageNum, pageSize));
    }
}
