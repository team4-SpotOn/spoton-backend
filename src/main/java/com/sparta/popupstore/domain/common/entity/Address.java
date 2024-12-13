package com.sparta.popupstore.domain.common.entity;

import com.sparta.popupstore.domain.kakaoaddress.dto.KakaoAddressApiDto;
import com.sparta.popupstore.domain.kakaoaddress.dto.KakaoAddressApiDto.RoadAddress;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {
    private String address;
    private Double latitude;
    private Double longitude;

    public Address(String address, KakaoAddressApiDto kakaoAddressApiDto) {
        this.address = address;
        this.latitude = kakaoAddressApiDto.getDocuments().get(0).getRoadAddress().getLatitude();
        this.longitude = kakaoAddressApiDto.getDocuments().get(0).getRoadAddress().getLongitude();
    }

    public Address(String address) {
        this.address = address;
    }
}
