package com.sparta.popupstore.domain.popupstore.controller;

import com.sparta.popupstore.domain.popupstore.service.PopupStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PopupStoreController {

    private final PopupStoreService popupStoreService;
}
