package com.sparta.popupstore.s3.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class S3UrlResponseDto {
    private final String preSignedUrl;
    private final String imageUrl;

    @Builder
    public S3UrlResponseDto(String preSignedUrl, String imageUrl) {
        this.preSignedUrl = preSignedUrl;
        this.imageUrl = imageUrl;
    }
}
