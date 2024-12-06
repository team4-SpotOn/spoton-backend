package com.sparta.popupstore.domain.common.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {
    private Double latitude;
    private Double longitude;

    public Address(String address) {
    }
}
