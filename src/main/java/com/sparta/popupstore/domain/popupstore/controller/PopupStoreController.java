package com.sparta.popupstore.domain.popupstore.controller;

import com.sparta.popupstore.domain.popupstore.dto.request.PopupStoreCreateRequestDto;
import com.sparta.popupstore.domain.popupstore.dto.response.PopupStoreCreateResponseDto;
import com.sparta.popupstore.domain.popupstore.service.PopupStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/popupstores")
public class PopupStoreController {

    private final PopupStoreService popupStoreService;

    @PostMapping
    public ResponseEntity<PopupStoreCreateResponseDto> createPopupStore(
            @RequestParam String email,
            @RequestBody PopupStoreCreateRequestDto requestDto) {
        return ResponseEntity.ok(popupStoreService.createPopupStore(email, requestDto));
    }
}
