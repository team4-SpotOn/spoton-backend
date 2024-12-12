package com.sparta.popupstore.domain.promotionevent.dto.response;

import com.sparta.popupstore.domain.popupstore.dto.response.PopupStoreImageResponseDto;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class PromotionEventFindOnePopupStoreResponseDto {
    @Schema(description = "팝업스토어 고유번호")
    private final Long id;
    @Schema(description = "팝업스토어 명")
    private final String name;
    @Schema(description = "팝업스토어 가격")
    private final int price;
    @Schema(description = "팝업스토어 시작일")
    private final LocalDate startDate;
    @Schema(description = "팝업스토어 종료일")
    private final LocalDate endDate;
    @Schema(description = "수정된 팝업스토어 이미지 리스트")
    private final List<PopupStoreImageResponseDto> imageList;

    public PromotionEventFindOnePopupStoreResponseDto(PopupStore popupStore) {
        this.id = popupStore.getId();
        this.name = popupStore.getName();
        this.price = popupStore.getPrice();
        this.startDate = popupStore.getStartDate();
        this.endDate = popupStore.getEndDate();
        this.imageList = popupStore.getPopupStoreImageList().stream().map(PopupStoreImageResponseDto::new).toList();
    }
}
