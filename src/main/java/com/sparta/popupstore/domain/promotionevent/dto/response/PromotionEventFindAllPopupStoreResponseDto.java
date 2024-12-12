package com.sparta.popupstore.domain.promotionevent.dto.response;

import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class PromotionEventFindAllPopupStoreResponseDto {
    @Schema(description = "팝업스토어 고유 번호")
    private final Long id;
    @Schema(description = "팝업스토어 명")
    private final String name;
    @Schema(description = "팝업스토어 가격")
    private final int price;

    public PromotionEventFindAllPopupStoreResponseDto(PopupStore popupStore) {
        this.id = popupStore.getId();
        this.name = popupStore.getName();
        this.price = popupStore.getPrice();
    }
}
