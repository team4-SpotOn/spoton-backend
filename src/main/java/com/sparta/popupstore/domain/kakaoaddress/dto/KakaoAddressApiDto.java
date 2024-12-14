package com.sparta.popupstore.domain.kakaoaddress.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoAddressApiDto {

    @JsonProperty("documents")
    private List<Document> documents;

    @JsonProperty("meta")
    private Object meta;

}
