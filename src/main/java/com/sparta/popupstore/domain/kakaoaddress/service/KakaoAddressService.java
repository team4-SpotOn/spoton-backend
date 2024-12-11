package com.sparta.popupstore.domain.kakaoaddress.service;

import com.sparta.popupstore.domain.kakaoaddress.dto.KakaoAddressApiDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class KakaoAddressService {
    // 카카오 주소 api service
    @Value("${kakao.api.restful.key}")
    private String restAPIKey;
    public KakaoAddressApiDto getKakaoAddress(String address) {
        String url = "https://dapi.kakao.com/v2/local/search/address.json?query="+address;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", restAPIKey);
        RestClient restClient = RestClient.create();
        return restClient.get()
            .uri(url)
            .header("Authorization", restAPIKey)
            .retrieve()
            .body(KakaoAddressApiDto.class);
    }
}
