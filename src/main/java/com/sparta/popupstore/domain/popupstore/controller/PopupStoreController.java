package com.sparta.popupstore.domain.popupstore.controller;

import com.sparta.popupstore.domain.common.annotation.AuthCompany;
import com.sparta.popupstore.domain.common.annotation.AuthUser;
import com.sparta.popupstore.domain.common.annotation.CheckAdmin;
import com.sparta.popupstore.domain.company.entity.Company;
import com.sparta.popupstore.domain.popupstore.dto.request.PopupStoreCreateRequestDto;
import com.sparta.popupstore.domain.popupstore.dto.request.PopupStoreUpdateRequestDto;
import com.sparta.popupstore.domain.popupstore.dto.response.PopupStoreCreateResponseDto;
import com.sparta.popupstore.domain.popupstore.dto.response.PopupStoreFindOneResponseDto;
import com.sparta.popupstore.domain.popupstore.dto.response.PopupStoreUpdateResponseDto;
import com.sparta.popupstore.domain.popupstore.service.PopupStoreService;
import com.sparta.popupstore.domain.user.entity.User;
import com.sparta.popupstore.web.WebUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class PopupStoreController {

    private final PopupStoreService popupStoreService;

    @PostMapping("/popupstores")
    public ResponseEntity<PopupStoreCreateResponseDto> createPopupStore(
            @AuthCompany Company company,
            @RequestBody @Valid PopupStoreCreateRequestDto requestDto
    ) {
        return ResponseEntity.ok(popupStoreService.createPopupStore(company, requestDto));
    }

    @Operation(summary = "관리자 - 팝업 스토어 수정")
    @CheckAdmin
    @PatchMapping("/admin/popupstores/{popupStoreId}")
    public ResponseEntity<PopupStoreUpdateResponseDto> updatePopupStore(
            @PathVariable Long popupStoreId,
            @RequestPart("requestDto") @Valid PopupStoreUpdateRequestDto requestDto,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile
    ) {
        return ResponseEntity.ok(popupStoreService.updatePopupStore(popupStoreId, requestDto, imageFile));
    }

    @Operation(summary = "회사 - 팝업 스토어 수정")
    @PatchMapping("/popupstores/{popupStoreId}")
    public ResponseEntity<PopupStoreUpdateResponseDto> updatePopupStore(
            @PathVariable Long popupStoreId,
            @AuthCompany Company company,
            @RequestPart("requestDto") @Valid PopupStoreUpdateRequestDto requestDto,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile
    ) {
        return ResponseEntity.ok(popupStoreService.updatePopupStore(popupStoreId, company, requestDto, imageFile));
    }

    @Operation(summary = "팝업 스토어 단건 조회", description = "팝업스토어 단건조회(상세보기)")
    @GetMapping("/popupstores/{popupStoreId}")
    public ResponseEntity<PopupStoreFindOneResponseDto> getPopupStoreFindOne(
            @PathVariable Long popupStoreId,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return ResponseEntity.ok(popupStoreService.getPopupStoreOne(popupStoreId, request, response));
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
