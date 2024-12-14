package com.sparta.popupstore.domain.kakaoaddress.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class RoadAddress {
    @JsonProperty("address_name")
    private String addressName; // 도로명 주소

    @JsonProperty("x")
    private Double longitude;   // 경도

    @JsonProperty("y")
    private Double latitude;    // 위도
}
