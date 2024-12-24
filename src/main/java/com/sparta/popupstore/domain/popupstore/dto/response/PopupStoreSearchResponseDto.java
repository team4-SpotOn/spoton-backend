package com.sparta.popupstore.domain.popupstore.dto.response;

import com.sparta.popupstore.domain.common.entity.Address;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PopupStoreSearchResponseDto {
    @Schema(description = "팝업스토어 이름")
    private final String name;
    @Schema(description = "팝업스토어 시작일")
    private final LocalDate startDate;
    @Schema(description = "팝업스토어 종료일")
    private final LocalDate endDate;
    @Schema(description = "팝업스토어 조회수")
    private final Integer view;
    @Schema(description = "팝업스토어 입장료")
    private final Integer price;
    @Schema(description = "팝업스토어 내용")
    private final String contents;
    @Schema(description = "팝업스토어 주소")
    private final Address address;
    @Schema(description = "팝업스토어 썸네일")
    private final String thumbnail;

    public PopupStoreSearchResponseDto(
            PopupStore popupStore
    ) {
        this.name = popupStore.getName();
        this.startDate = popupStore.getStartDate();
        this.endDate = popupStore.getEndDate();
        this.view = popupStore.getView();
        this.price = popupStore.getPrice();
        this.contents = popupStore.getContents();
        this.address = popupStore.getAddress();
        this.thumbnail = popupStore.getThumbnail();
    }
}
