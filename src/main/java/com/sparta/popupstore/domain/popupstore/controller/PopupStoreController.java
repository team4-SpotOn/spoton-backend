package com.sparta.popupstore.domain.popupstore.controller;

import com.sparta.popupstore.domain.common.annotation.AuthCompany;
import com.sparta.popupstore.domain.company.entity.Company;
import com.sparta.popupstore.domain.popupstore.dto.request.PopupStoreCreateRequestDto;
import com.sparta.popupstore.domain.popupstore.dto.request.PopupStoreUpdateRequestDto;
import com.sparta.popupstore.domain.popupstore.dto.response.PopupStoreCreateResponseDto;
import com.sparta.popupstore.domain.popupstore.dto.response.PopupStoreGetOneResponseDto;
import com.sparta.popupstore.domain.popupstore.dto.response.PopupStoreSearchResponseDto;
import com.sparta.popupstore.domain.popupstore.dto.response.PopupStoreUpdateResponseDto;
import com.sparta.popupstore.domain.popupstore.enums.PopupStoreStatus;
import com.sparta.popupstore.domain.popupstore.service.PopupStoreService;
import com.sparta.popupstore.web.WebUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "팝업스토어 API", description = "팝업스토어 CRUD API")
@RequestMapping("/popupstores")
public class PopupStoreController {

    private final PopupStoreService popupStoreService;

    @Operation(summary = "팝업스토어 생성")
    @Parameter(name = "name", description = "팝업스토어 명")
    @Parameter(name = "startDate", description = "팝업스토어 시작일")
    @Parameter(name = "endDate", description = "팝업스토어 종료일")
    @Parameter(name = "contents", description = "팝업스토어 내용")
    @Parameter(name = "price", description = "팝업스토어 가격")
    @Parameter(name = "address", description = "팝업스토어 주소")
    @Parameter(name = "imageList", description = "이미지 저장된 경로와 이미지 순서 List Dto")
    @Parameter(name = "operatingList", description = "팝업스토어 운영 시간 List Dto")
    @Parameter(name = "attributeList", description = "팝업스토어 특성 List Dto")
    @Parameter(name = "company", description = "로그인한 회사")
    @PostMapping
    public ResponseEntity<PopupStoreCreateResponseDto> createPopupStore(
            @AuthCompany Company company,
            @RequestBody PopupStoreCreateRequestDto requestDto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(popupStoreService.createPopupStore(company, requestDto));
    }

    @Operation(summary = "팝업 스토어 단건 조회", description = "팝업스토어 단건조회(상세보기)")
    @Parameter(name = "popupStoreId", description = "조회할 팝업스토어 기본키")
    @GetMapping("/{popupStoreId}")
    public ResponseEntity<PopupStoreGetOneResponseDto> getPopupStoreOne(
            @PathVariable Long popupStoreId,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        boolean view = WebUtil.checkCookie(request, response, popupStoreId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(popupStoreService.getPopupStoreOne(popupStoreId, view));
    }

    @Operation(summary = "팝업 스토어 전체 조회", description = "팝업 스토어 전체목록")
    @GetMapping
    public ResponseEntity<List<PopupStoreSearchResponseDto>> getPopupStoreAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(popupStoreService.getPopupStoreAll());
    }

    @Operation(summary = "현재 날짜 기준 진행중, 시작예정, 종료된 팝업스토어 조회", description = "현재 날짜 기준 진행중, 시작예정, 종료된 팝업스토어 조회")
    @Parameter(name = "page", description = "페이지 번호")
    @Parameter(name = "size", description = "페이지 사이즈")
    @Parameter(name = "popupStoreStatus", description = "ALL, OPEN, CLOSE, SCHEDULE / 전체, 진행중, 종료된, 시작 예정")
    @GetMapping("/search/{popupStoreStatus}")
    public ResponseEntity<Page<PopupStoreSearchResponseDto>> getPopupStoreByStatus(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @PathVariable PopupStoreStatus popupStoreStatus
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(popupStoreService.getPopupStoreByStatus(page, size, popupStoreStatus));
    }

    @Operation(summary = "팝업 스토어 수정")
    @Parameter(name = "name", description = "수정할 팝업스토어 명")
    @Parameter(name = "startDate", description = "수정할 팝업스토어 시작일")
    @Parameter(name = "endDate", description = "수정할 팝업스토어 종료일")
    @Parameter(name = "contents", description = "수정할 팝업스토어 내용")
    @Parameter(name = "price", description = "수정할 팝업스토어 가격")
    @Parameter(name = "address", description = "수정할 팝업스토어 주소")
    @Parameter(name = "imageList", description = "수정할 이미지 저장된 경로와 이미지 순서 List Dto")
    @Parameter(name = "operatingList", description = "수정할 팝업스토어 운영 시간 List Dto")
    @Parameter(name = "attributeList", description = "수정할 팝업스토어 특성 List Dto")
    @Parameter(name = "popupStoreId", description = "수정할 팝업 스토어 기본키")
    @Parameter(name = "company", description = "로그인한 회사")
    @PatchMapping("/{popupStoreId}")
    public ResponseEntity<PopupStoreUpdateResponseDto> updatePopupStore(
            @PathVariable Long popupStoreId,
            @AuthCompany Company company,
            @RequestBody @Valid PopupStoreUpdateRequestDto requestDto
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(popupStoreService.updatePopupStore(popupStoreId, company, requestDto));
    }

    @Operation(summary = "팝업스토어 삭제")
    @Parameter(name = "company", description = "로그인한 회사")
    @Parameter(name = "popupStoreId", description = "삭제할 팝업 스토어 기본키")
    @DeleteMapping("/{popupStoreId}")
    public ResponseEntity<Void> deletePopupStore(
            @AuthCompany Company company,
            @PathVariable("popupStoreId") Long popupStoreId
    ) {
        popupStoreService.deletePopupStore(company, popupStoreId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Operation(summary = "팝업스토어 날짜별 조회", description = "시작일과 종료일 사이에 운영하는 팝업스토어를 조회합니다.")
    @Parameter(name = "startDate", description = "시작일")
    @Parameter(name = "endDate", description = "종료일")
    @Parameter(name = "page", description = "페이지 번호")
    @Parameter(name = "size", description = "페이지 사이즈")
    @GetMapping("/period")
    public ResponseEntity<List<PopupStoreSearchResponseDto>> findStorePeriod(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam(name = "page", required = false, defaultValue = "1") Long page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Long size) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(popupStoreService.findStorePeriod(startDate, endDate, page, size));
    }

    @Operation(summary = "팝업스토어 이번달 조회", description = "이번달에 진행중인 팝업스토어를 조회합니다.")
    @Parameter(name = "page", description = "페이지 번호")
    @Parameter(name = "size", description = "페이지 사이즈")
    @GetMapping("/month")
    public ResponseEntity<List<PopupStoreSearchResponseDto>> findStoreMonth(
            @RequestParam(name = "page", required = false, defaultValue = "1") Long page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Long size) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(popupStoreService.findStoreMonth(page, size));
    }
}
