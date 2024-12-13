package com.sparta.popupstore.domain.popupstore.controller;

import com.sparta.popupstore.domain.common.annotation.AuthCompany;
import com.sparta.popupstore.domain.common.annotation.CheckAdmin;
import com.sparta.popupstore.domain.company.entity.Company;
import com.sparta.popupstore.domain.popupstore.dto.request.PopupStoreCreateRequestDto;
import com.sparta.popupstore.domain.popupstore.dto.request.PopupStoreUpdateRequestDto;
import com.sparta.popupstore.domain.popupstore.dto.response.PopupStoreCreateResponseDto;
import com.sparta.popupstore.domain.popupstore.dto.response.PopupStoreFindOneResponseDto;
import com.sparta.popupstore.domain.popupstore.dto.response.PopupStoreUpdateResponseDto;
import com.sparta.popupstore.domain.popupstore.service.PopupStoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "PopupStore", description = "팝업스토어 관련 api")
@RestController
@RequiredArgsConstructor
public class PopupStoreController {

    private final PopupStoreService popupStoreService;

    @Operation(summary = "회사 - 팝업스토어 생성")
    @Parameter(name = "name", description = "팝업스토어 명")
    @Parameter(name = "content", description = "팝업스토어 내용")
    @Parameter(name = "price", description = "팝업스토어 가격")
    @Parameter(name = "address", description = "팝업스토어 주소")
    @Parameter(name = "startDate", description = "팝업스토어 시작일")
    @Parameter(name = "endDate", description = "팝업스토어 종료일")
    @Parameter(name = "startTime", description = "팝업스토어 개장시간")
    @Parameter(name = "endTime", description = "팝업스토어 폐장시간")
    @Parameter(name = "images", description = "이미지 명과 이미지 순서가 들어있는 List Dto")
    @PostMapping("/popupstores")
    public ResponseEntity<PopupStoreCreateResponseDto> createPopupStore(
            @AuthCompany Company company,
            @RequestBody @Valid PopupStoreCreateRequestDto requestDto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(popupStoreService.createPopupStore(company, requestDto));
    }

    @Operation(summary = "전체 - 팝업 스토어 단건 조회", description = "팝업스토어 단건조회(상세보기)")
    @GetMapping("/popupstores/{popupStoreId}")
    public ResponseEntity<PopupStoreFindOneResponseDto> getPopupStoreFindOne(
            @PathVariable Long popupStoreId,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(popupStoreService.getPopupStoreOne(popupStoreId, request, response));
    }

    // 팝업스토어 전체목록 지도용
    @Operation(summary = "임시 팝업 전제조회(지도용)", description = "지도 팝업스토어 전체목록")
    @GetMapping("/popupstores")
    public ResponseEntity<List<PopupStoreFindOneResponseDto>> getPopupStoreAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(popupStoreService.getPopupStoreAll());
    }

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
    @PatchMapping("/admin/popupstores/{popupStoreId}")
    public ResponseEntity<PopupStoreUpdateResponseDto> updatePopupStore(
            @PathVariable Long popupStoreId,
            @RequestBody @Valid PopupStoreUpdateRequestDto requestDto
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(popupStoreService.updatePopupStore(popupStoreId, requestDto));
    }

    @Operation(summary = "회사 - 팝업 스토어 수정")
    @Parameter(name = "name", description = "수정할 팝업스토어 명")
    @Parameter(name = "content", description = "수정할 팝업스토어 내용")
    @Parameter(name = "price", description = "수정할 팝업스토어 가격")
    @Parameter(name = "address", description = "수정할 팝업스토어 주소")
    @Parameter(name = "startDate", description = "수정할 팝업스토어 시작일")
    @Parameter(name = "endDate", description = "수정할 팝업스토어 종료일")
    @Parameter(name = "startTime", description = "수정할 팝업스토어 개장시간")
    @Parameter(name = "endTime", description = "수정할 팝업스토어 폐장시간")
    @Parameter(name = "images", description = "수정할 이미지 명과 이미지 순서가 들어있는 List Dto")
    @PatchMapping("/popupstores/{popupStoreId}")
    public ResponseEntity<PopupStoreUpdateResponseDto> updatePopupStore(
            @PathVariable Long popupStoreId,
            @AuthCompany Company company,
            @RequestBody @Valid PopupStoreUpdateRequestDto requestDto
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(popupStoreService.updatePopupStore(popupStoreId, company, requestDto));
    }

    @Operation(summary = "회사 - 팝업스토어 삭제", description = "popupStoreId에 해당하는 팝업스토어를 삭제합니다.")
    @Parameter(name = "company", description = "로그인한 회사")
    @Parameter(name = "popupStoreId", description = "팝업 스토어 고유번호")
    @DeleteMapping("/popupstores/{popupStoreId}")
    public ResponseEntity<Void> deletePopupStore(
            @AuthCompany Company company,
            @PathVariable("popupStoreId") Long popupStoreId
    ) {
        popupStoreService.deletePopupStore(company, popupStoreId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Operation(summary = "관리자 - 팝업스토어 삭제", description = "popupStoreId에 해당하는 팝업스토어를 삭제합니다.")
    @Parameter(name = "user", description = "로그인한 관리자")
    @Parameter(name = "popupStoreId", description = "팝업 스토어 고유번호")
    @CheckAdmin
    @DeleteMapping("/admin/popupstores/{popupStoreId}")
    public ResponseEntity<Void> deletePopupStoreAdmin(
            @PathVariable("popupStoreId") Long popupStoreId
    ) {
        popupStoreService.deletePopupStore(popupStoreId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
