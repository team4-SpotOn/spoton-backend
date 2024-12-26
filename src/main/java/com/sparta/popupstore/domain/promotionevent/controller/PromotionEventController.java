package com.sparta.popupstore.domain.promotionevent.controller;

import com.sparta.popupstore.domain.promotionevent.dto.response.PromotionEventFindAllResponseDto;
import com.sparta.popupstore.domain.promotionevent.dto.response.PromotionEventFindOneResponseDto;
import com.sparta.popupstore.domain.promotionevent.service.PromotionEventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "PromotionEvents", description = "프로모션 이벤트 관련 API")
@RequestMapping("/promotionEvents")
public class PromotionEventController {

    private final PromotionEventService promotionEventService;

    @Operation(summary = "프로모션 이벤트 다건 조회", description = "현재 등록되어 있는 이벤트들을 보여줍니다.")
    @Parameter(name = "page", description = "페이지 번호")
    @Parameter(name = "size", description = "페이지 사이즈")
    @GetMapping
    public ResponseEntity<Page<PromotionEventFindAllResponseDto>> findAllPromotionalEvents(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(promotionEventService.findAllPromotionalEvents(page, size));
    }

    @Operation(summary = "프로모션 이벤트 단건 조회")
    @Parameter(name = "promotionEventId", description = "조회 할 프로모션 이벤트의 기본키")
    @GetMapping("/{promotionEventId}")
    public ResponseEntity<PromotionEventFindOneResponseDto> findOnePromotionalEvent(
            @PathVariable(name = "promotionEventId") Long promotionEventId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(promotionEventService.findOnePromotionEvent(promotionEventId));
    }
}
