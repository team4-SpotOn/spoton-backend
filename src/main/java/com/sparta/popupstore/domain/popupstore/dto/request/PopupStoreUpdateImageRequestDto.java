package com.sparta.popupstore.domain.popupstore.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PopupStoreUpdateImageRequestDto {
    private Long id;
    @NotBlank(message = "하나 이상의 이미지를 등록하셔야합니다.")
    private String imageUrl;
    @Min(value = 0, message = "0 이상의 수를 입력해주세요")
    private int sort;
}
