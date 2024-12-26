package com.sparta.popupstore.s3.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.List;

@Getter
public class S3ImageListRequestDto {
    @NotEmpty(message = "이미지를 하나이상 올려주세요")
    List<S3ImageRequestDto> imageRequestDtoList;
}
