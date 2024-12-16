package com.sparta.popupstore.domain.kakaoaddress.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoadAddress {
    @JsonProperty("address_name")
    private String addressName; // 도로명 주소

    @JsonProperty("x")
    private Double longitude;   // 경도

    @JsonProperty("y")
    private Double latitude;    // 위도
}
