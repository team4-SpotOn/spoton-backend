package com.sparta.popupstore.domain.admin.controller;

import com.sparta.popupstore.domain.admin.dto.request.AdminSigninRequestDto;
import com.sparta.popupstore.domain.admin.dto.request.AdminSignupRequestDto;
import com.sparta.popupstore.domain.admin.dto.response.AdminSignupResponseDto;
import com.sparta.popupstore.domain.admin.entity.Admin;
import com.sparta.popupstore.domain.admin.service.AdminService;
import com.sparta.popupstore.domain.admin.type.AdminRole;
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
import com.sparta.popupstore.jwt.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "관리자 API", description = "관리자 계정의 회원가입 로그인 및 관리자 권한이 필요한 API.")
@RequestMapping("/admins")
public class AdminController {

    private final AdminService adminService;
    private final PopupStoreService popupStoreService;
    private final PromotionEventService promotionEventService;
    private final JwtUtil jwtUtil;

    @Operation(summary = "관리자 회원 가입", description = "관리자 계정 회원 가입")
    @Parameter(name = "signinId", description = "회원 가입 아이디")
    @Parameter(name = "password", description = "회원 가입 비밀번호")
    @PostMapping("/signup")
    public ResponseEntity<AdminSignupResponseDto> signup(
            @RequestBody AdminSignupRequestDto requestDto,
            HttpServletResponse response
    ) {
        AdminSignupResponseDto responseDto = adminService.signup(requestDto);
        jwtUtil.addJwtToCookie(responseDto.getSigninId(), AdminRole.ADMIN, response);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDto);
    }

    @Operation(summary = "관리자 로그인", description = "관리자 계정 로그인")
    @Parameter(name = "signinId", description = "로그인 아이디")
    @Parameter(name = "password", description = "로그인 비밀번호")
    @PostMapping("/signin")
    public ResponseEntity<Void> signin(
            @RequestBody AdminSigninRequestDto requestDto,
            HttpServletResponse response
    ) {
        Admin admin = adminService.signin(requestDto);
        jwtUtil.addJwtToCookie(admin.getSigninId(), AdminRole.ADMIN, response);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @Operation(summary = "관리자 - 팝업 스토어 수정")
    @Parameter(name = "name", description = "팝업스토어 명")
    @Parameter(name = "startDate", description = "팝업스토어 시작일")
    @Parameter(name = "endDate", description = "팝업스토어 종료일")
    @Parameter(name = "contents", description = "팝업스토어 내용")
    @Parameter(name = "price", description = "팝업스토어 가격")
    @Parameter(name = "address", description = "팝업스토어 주소")
    @Parameter(name = "imageList", description = "이미지 저장된 경로와 이미지 순서 List Dto")
    @Parameter(name = "operatingList", description = "팝업스토어 운영 시간 List Dto")
    @Parameter(name = "attributeList", description = "팝업스토어 특성 List Dto")
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
    @Parameter(name = "startDateTime", description = "시작 시각")
    @Parameter(name = "endDateTime", description = "종료 시각")
    @Parameter(name = "imageUrl", description = "이미지 저장된 경로")
    @Parameter(name = "popupStoreId", description = "이벤트 대상 팝업 스토어 고유번호 / 전체를 대상으로 진행하는 이벤트 일 시 null")
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
    @Parameter(name = "promotionEventId", description = "조회 할 프로모션 이벤트의 기본키")
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
    @Parameter(name = "startDateTime", description = "시작 시각")
    @Parameter(name = "endDateTime", description = "종료 시각")
    @Parameter(name = "imageUrl", description = "이미지 저장된 경로")
    @Parameter(name = "promotionEventId", description = "수정할 프로모션 이벤트의 기본키")
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
    @Parameter(name = "promotionEventId", description = "삭제할 프로모션 이벤트의 기본키")
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
