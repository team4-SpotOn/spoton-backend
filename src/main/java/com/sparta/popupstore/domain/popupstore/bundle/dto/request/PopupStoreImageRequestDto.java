package com.sparta.popupstore.domain.popupstore.bundle.dto.request;

import com.sparta.popupstore.domain.popupstore.bundle.entity.PopupStoreImage;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PopupStoreImageRequestDto {
    @NotBlank(message = "하나 이상의 이미지를 등록하셔야합니다.")
    private String imageUrl;
    @Min(value = 0, message = "0 이상의 수만 입력해주세요")
    private Integer sort;

    public PopupStoreImage toEntity(PopupStore popupStore) {
        return PopupStoreImage.builder()
                .popupStore(popupStore)
                .imageUrl(this.imageUrl)
                .sort(this.sort)
                .build();
    }
}
