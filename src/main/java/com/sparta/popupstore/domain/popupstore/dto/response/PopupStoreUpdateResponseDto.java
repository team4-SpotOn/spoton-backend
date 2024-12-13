package com.sparta.popupstore.domain.popupstore.dto.response;

import com.sparta.popupstore.domain.common.entity.Address;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.entity.PopupStoreAttribute;
import com.sparta.popupstore.domain.popupstore.entity.PopupStoreImage;
import com.sparta.popupstore.domain.popupstore.entity.PopupStoreOperating;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class PopupStoreUpdateResponseDto {
    @Schema(description = "수정된 팝업스토어 명")
    private final String name;
    @Schema(description = "수정된 팝업스토어 시작일")
    private final LocalDate startDate;
    @Schema(description = "수정된 팝업스토어 종료일")
    private final LocalDate endDate;
    @Schema(description = "수정된 팝업스토어 가격")
    private final int price;
    @Schema(description = "수정된 팝업스토어 내용")
    private final String contents;
    @Schema(description = "수정된 팝업스토어 주소")
    private final Address address;
    @Schema(description = "수정된 팝업스토어 이미지 리스트")
    private final List<PopupStoreImageResponseDto> imageList;
    @Schema(description = "생성된 팝업스토어 운영시간")
    private final List<PopupStoreOperatingResponseDto> operatingList;
    @Schema(description = "생성된 팝업스토어 속성 리스트")
    private final List<PopupStoreAttributeResponseDto> attributeList;

    public PopupStoreUpdateResponseDto(
            PopupStore popupStore,
            List<PopupStoreImage> imageList,
            List<PopupStoreOperating> operatingList,
            List<PopupStoreAttribute> attributeList
    ) {
        this.name = popupStore.getName();
        this.startDate = popupStore.getStartDate();
        this.endDate = popupStore.getEndDate();
        this.price = popupStore.getPrice();
        this.contents = popupStore.getContents();
        this.address = popupStore.getAddress();
        this.imageList = imageList.stream().map(PopupStoreImageResponseDto::new).toList();
        this.operatingList = operatingList.stream().map(PopupStoreOperatingResponseDto::new).toList();
        this.attributeList = attributeList.stream().map(PopupStoreAttributeResponseDto::new).toList();
    }
}
