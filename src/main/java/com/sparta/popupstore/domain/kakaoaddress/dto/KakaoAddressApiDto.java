package com.sparta.popupstore.domain.kakaoaddress.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;

@Getter
public class KakaoAddressApiDto {

    @JsonProperty("documents")
    private List<Document> documents;

    @JsonProperty("meta")
    private Object meta;

    @Getter
    public static class Document {
        // road_address 필드
        @JsonProperty("road_address")
        private RoadAddress roadAddress;
    }

    @Getter
    public static class RoadAddress {
        @JsonProperty("address_name")
        private String addressName; // 도로명 주소

        @JsonProperty("x")
        private Double longitude;   // 경도

        @JsonProperty("y")
        private Double latitude;    // 위도
    }


    public KakaoAddressApiDto() {
    }
}
