package com.sparta.popupstore.domain.popupstore.dto.response;

import com.sparta.popupstore.domain.common.entity.Address;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.entity.PopupStoreImage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class PopupStoreFindOneResponseDto {
    @Schema(description = "팝업스토어 이름")
    private final String name;
    @Schema(description = "팝업스토어 시작일")
    private final LocalDate startDate;
    @Schema(description = "팝업스토어 종료일")
    private final LocalDate endDate;
    @Schema(description = "팝업스토어 조회수")
    private final int view;
    @Schema(description = "팝업스토어 입장료")
    private final int price;
    @Schema(description = "팝업스토어 내용")
    private final String contents;
    @Schema(description = "팝업스토어 주소")
    private final Address address;
    @Schema(description = "팝업스토어 이미지 리스트")
    private final List<PopupStoreImageResponseDto> imageList;

    public PopupStoreFindOneResponseDto(PopupStore popupStore, List<PopupStoreImage> imageList) {
        this.name = popupStore.getName();
        this.startDate = popupStore.getStartDate();
        this.endDate = popupStore.getEndDate();
        this.view = popupStore.getView();
        this.price = popupStore.getPrice();
        this.contents = popupStore.getContents();
        this.address = popupStore.getAddress();
        this.imageList = imageList.stream().map(PopupStoreImageResponseDto::new).toList();
    }
}
