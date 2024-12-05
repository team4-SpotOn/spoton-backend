package com.sparta.popupstore.domain.popupstore.controller;

import com.sparta.popupstore.domain.common.annotation.AuthCompany;
import com.sparta.popupstore.domain.common.annotation.AuthUser;
import com.sparta.popupstore.domain.company.entity.Company;
import com.sparta.popupstore.domain.popupstore.dto.request.PopupStoreCreateRequestDto;
import com.sparta.popupstore.domain.popupstore.dto.request.PopupStoreUpdateRequestDto;
import com.sparta.popupstore.domain.popupstore.dto.response.PopupStoreCreateResponseDto;
import com.sparta.popupstore.domain.popupstore.dto.response.PopupStoreFindOneResponseDto;
import com.sparta.popupstore.domain.popupstore.dto.response.PopupStoreUpdateResponseDto;
import com.sparta.popupstore.domain.popupstore.service.PopupStoreService;
import com.sparta.popupstore.domain.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class PopupStoreController {

    private final PopupStoreService popupStoreService;

    @PostMapping("/popupstores")
    public ResponseEntity<PopupStoreCreateResponseDto> createPopupStore(
            @AuthCompany Company company,
            @RequestPart("requestDto") @Valid PopupStoreCreateRequestDto requestDto,
            @RequestPart("imageFile") MultipartFile imageFile
    ) throws IOException {
        return ResponseEntity.ok(popupStoreService.createPopupStore(company, requestDto, imageFile));
    }

    @Operation(summary = "관리자 - 팝업 스토어 수정")
    @PatchMapping("/admin/popupstores/{popupStoreId}")
    public ResponseEntity<PopupStoreUpdateResponseDto> updatePopupStore(
            @PathVariable Long popupStoreId,
            @AuthUser User user,
            @RequestPart("requestDto") @Valid PopupStoreUpdateRequestDto requestDto,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile
    ) {
        return ResponseEntity.ok(popupStoreService.updatePopupStore(popupStoreId, user, requestDto, imageFile));
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
    @GetMapping("/{popupId}")
    public ResponseEntity<PopupStoreFindOneResponseDto> getPopupStoreFindOne(@PathVariable Long popupId) {
        return ResponseEntity.ok(popupStoreService.getPopupStoreFindOne(popupId));
    }
}
