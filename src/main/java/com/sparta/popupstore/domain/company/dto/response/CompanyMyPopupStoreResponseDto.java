package com.sparta.popupstore.domain.company.dto.response;

import com.sparta.popupstore.domain.common.entity.Address;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class CompanyMyPopupStoreResponseDto {
    @Schema(description = "자사 팝업스토어 고유 번호")
    private final Long id;

    @Schema(description = "자사 팝업스토어 이름")
    private final String name;

    @Schema(description = "자사 팝업스토어 시작일")
    private final LocalDate startDate;

    @Schema(description = "자사 팝업스토어 종료일")
    private final LocalDate endDate;

    @Schema(description = "자사 팝업스토어 입장료")
    private final Integer price;

    @Schema(description = "자사 팝업스토어 내용")
    private final String contents;

    @Schema(description = "자사 팝업스토어 주소")
    private final Address address;

    public CompanyMyPopupStoreResponseDto(PopupStore popupStore) {
        this.id = popupStore.getId();
        this.name = popupStore.getName();
        this.startDate = popupStore.getStartDate();
        this.endDate = popupStore.getEndDate();
        this.price = popupStore.getPrice();
        this.contents = popupStore.getContents();
        this.address = popupStore.getAddress();
    }


}
