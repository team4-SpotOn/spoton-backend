package com.sparta.popupstore.domain.kakaoaddress.controller;

import com.sparta.popupstore.domain.common.entity.Address;
import com.sparta.popupstore.domain.kakaoaddress.service.KakaoAddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "카카오 주소 API", description = "주소를 지도에 찍어주는 API")
public class KakaoAddressController {

    private final KakaoAddressService kakaoAddressService;

    @Operation(summary = "카카오 주소 API", description = "주소 기준 위도/경도 구하기")
    @GetMapping("/kakaoapi")
    public Address getKakaoAddress(@RequestParam String address) {
        return kakaoAddressService.getKakaoAddress(address);
    }
}
