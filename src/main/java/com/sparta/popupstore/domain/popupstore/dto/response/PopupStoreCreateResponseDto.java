package com.sparta.popupstore.domain.popupstore.dto.response;

import com.sparta.popupstore.domain.common.entity.Address;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import io.swagger.v3.oas.annotations.media.Schema;
import com.sparta.popupstore.domain.popupstore.entity.PopupStoreOperating;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class PopupStoreCreateResponseDto {
    @Schema(description = "생성된 팝업스토어 명")
    private final String name;
    @Schema(description = "생성된 팝업스토어 내용")
    private final String content;
    @Schema(description = "생성된 팝업스토어 가격")
    private final int price;
    @Schema(description = "생성된 팝업스토어 주소")
    private final Address address;
    @Schema(description = "생성된 팝업스토어 시작일")
    private final LocalDate startDate;
    @Schema(description = "생성된 팝업스토어 종료일")
    private final LocalDate endDate;
    @Schema(description = "생성된 팝업스토어 이미지 리스트")
    private final List<PopupStoreImageResponseDto> imageList;
    @Schema(description = "생성된 팝업스토어 운영시간")
    private List<PopupStoreOperating> popupStoreOperatingList;


    public PopupStoreCreateResponseDto(PopupStore popupStore, List<PopupStoreOperating> popupStoreOperatingList) {
        this.name = popupStore.getName();
        this.content = popupStore.getContents();
        this.price = popupStore.getPrice();
        this.address = popupStore.getAddress();
        this.startDate = popupStore.getStartDate();
        this.endDate = popupStore.getEndDate();
        this.popupStoreOperatingList = popupStoreOperatingList;
        this.imageList = popupStore.getPopupStoreImageList().stream().map(PopupStoreImageResponseDto::new).toList();
    }
}
