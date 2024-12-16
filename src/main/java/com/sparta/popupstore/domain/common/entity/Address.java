package com.sparta.popupstore.domain.common.entity;

import com.sparta.popupstore.domain.kakaoaddress.dto.RoadAddress;
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

    public Address(String address, RoadAddress roadAddress) {
        this.address = address;
        this.latitude = roadAddress.getLatitude();
        this.longitude = roadAddress.getLongitude();
    }

    public Address(String address) {
        this.address = address;
    }
}
