package com.sparta.popupstore.domain.popupstore.dto.request;

import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.entity.PopupStoreImage;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PopupStoreImageRequestDto {
    @NotBlank(message = "하나 이상의 이미지를 등록하셔야합니다.")
    private String imageUrl;
    @Min(value = 0, message = "0 이상의 수만 입력해주세요")
    private int sort;

    public PopupStoreImage toEntity(PopupStore popupStore) {
        return PopupStoreImage.builder()
                .popupStore(popupStore)
                .imageUrl(this.imageUrl)
                .sort(this.sort)
                .build();
    }
}
