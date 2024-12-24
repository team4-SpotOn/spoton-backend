package com.sparta.popupstore.s3.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
public class S3ImageResponseDto {
    @Schema(description = "preSignedUrl")
    private final String preSignedUrl;
    @Schema(description = "preSignedUrl 을 통해 이미지가 저장될 경로")
    private final String imageUrl;

    @Builder
    public S3ImageResponseDto(String preSignedUrl, String imageUrl) {
        this.preSignedUrl = preSignedUrl;
        this.imageUrl = imageUrl;
    }
}
