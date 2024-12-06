package com.sparta.popupstore.domain.popupstore.dto.response;

import com.sparta.popupstore.domain.common.entity.Address;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class PopupStoreFindOneResponseDto {

    @Schema(description = "팝업스토어 이름")
    private String name;

    @Schema(description = "팝업스토어 시작일")
    private LocalDate startDate;

    @Schema(description = "팝업스토어 종료일")
    private LocalDate endDate;

    @Schema(description = "팝업스토어 입장료")
    private int price;

    @Schema(description = "팝업스토어 내용")
    private String contents;


    @Schema(description = "팝업스토어 주소")
    private Address address;

    public PopupStoreFindOneResponseDto(PopupStore popupStore) {
        this.name = popupStore.getName();
        this.startDate = popupStore.getStartDate();
        this.endDate = popupStore.getEndDate();
        this.price = popupStore.getPrice();
        this.contents = popupStore.getContents();
        this.address = popupStore.getAddress();
    }

}