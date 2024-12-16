package com.sparta.popupstore.domain.kakaoaddress.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Document {
    // road_address 필드
    @JsonProperty("road_address")
    private RoadAddress roadAddress;
}
