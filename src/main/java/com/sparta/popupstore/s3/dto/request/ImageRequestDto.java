package com.sparta.popupstore.s3.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ImageRequestDto {
    @NotBlank(message = "업로드할 파일 명을 제대로 입력해주세요.")
    private String fileName;
}
