package com.sparta.popupstore.domain.popupstore.controller;

import com.sparta.popupstore.domain.common.annotation.AuthCompany;
import com.sparta.popupstore.domain.company.entity.Company;
import com.sparta.popupstore.domain.popupstore.dto.request.PopupStoreCreateRequestDto;
import com.sparta.popupstore.domain.popupstore.dto.request.PopupStoreUpdateRequestDto;
import com.sparta.popupstore.domain.popupstore.dto.response.PopupStoreCreateResponseDto;
import com.sparta.popupstore.domain.popupstore.dto.response.PopupStoreUpdateResponseDto;
import com.sparta.popupstore.domain.popupstore.service.PopupStoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/popupstores")
public class PopupStoreController {

    private final PopupStoreService popupStoreService;

    @PostMapping
    public ResponseEntity<PopupStoreCreateResponseDto> createPopupStore(
            @AuthCompany Company company,
            @RequestPart("requestDto") @Valid PopupStoreCreateRequestDto requestDto,
            @RequestPart("imageFile") MultipartFile imageFile
    ) throws IOException {
        return ResponseEntity.ok(popupStoreService.createPopupStore(company, requestDto, imageFile));
    }


    @PatchMapping("/{popupId}")
    public ResponseEntity<PopupStoreUpdateResponseDto> updatePopupStore(
            @PathVariable Long popupId,
            @AuthCompany Company company,
            @RequestPart("requestDto") @Valid PopupStoreUpdateRequestDto requestDto,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile
    ) {
        PopupStoreUpdateResponseDto responseDto = null;
        responseDto = popupStoreService.updatePopupStore(popupId, company, requestDto, imageFile);
        return ResponseEntity.ok(responseDto);
    }
}
