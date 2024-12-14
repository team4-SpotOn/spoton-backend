package com.sparta.popupstore.domain.kakaoaddress.service;

import com.sparta.popupstore.domain.common.entity.Address;
import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.domain.kakaoaddress.dto.KakaoAddressApiDto;
import com.sparta.popupstore.domain.kakaoaddress.dto.RoadAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KakaoAddressService {
    private final static String KAKAO_ADDRESS_API_URL = "https://kapi.kakao.com/v2/address/.json?query=";
    private final RestClient restClient;
    // 카카오 주소 api service
    @Value("${kakao.api.restful.key}")
    private String restAPIKey;

    public Address getKakaoAddress(String address) {
        String url = KAKAO_ADDRESS_API_URL + address;

        RoadAddress roadAddress = Optional.ofNullable(
                        restClient.get()
                                .uri(url)
                                .header("Authorization", restAPIKey)
                                .retrieve()
                                .body(KakaoAddressApiDto.class)
                ).orElseThrow(() -> new CustomApiException(ErrorCode.KAKAO_ADDRESS_API_ERROR))
                .getDocuments().get(0).getRoadAddress();

        return new Address(address, roadAddress);
    }
}
