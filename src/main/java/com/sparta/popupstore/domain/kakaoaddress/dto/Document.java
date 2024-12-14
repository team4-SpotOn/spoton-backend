package com.sparta.popupstore.domain.kakaoaddress.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Document {
    // road_address 필드
    @JsonProperty("road_address")
    private RoadAddress roadAddress;
}
