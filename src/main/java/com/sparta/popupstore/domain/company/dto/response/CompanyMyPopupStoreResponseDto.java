package com.sparta.popupstore.domain.company.dto.response;

import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;

@Getter
public class CompanyMyPopupStoreResponseDto {
    @Schema(description = "자사 팝업스토어 고유 번호")
    private Long id;

    @Schema(description = "자사 팝업스토어 이름")
    private String name;

    @Schema(description = "자사 팝업스토어 시작일")
    private LocalDate startDate;

    @Schema(description = "자사 팝업스토어 종료일")
    private LocalDate endDate;

    @Schema(description = "자사 팝업스토어 시작시간")
    private LocalTime startTime;

    @Schema(description = "자사 팝업스토어 종료시간")
    private LocalTime endTime;

    @Schema(description = "자사 팝업스토어 입장료")
    private int price;
    
    @Schema(description = "자사 팝업스토어 내용")
    private String contents;

    @Schema(description = "자사 팝업스토어 이미지")
    private String image;

    @Schema(description = "자사 팝업스토어 주소")
    private String address;

    public CompanyMyPopupStoreResponseDto(PopupStore popupStore) {
        this.id = popupStore.getId();
        this.name = popupStore.getName();
        this.startDate = popupStore.getStartDate();
        this.endDate = popupStore.getEndDate();
        this.startTime = popupStore.getStartTime();
        this.endTime = popupStore.getEndTime();
        this.price = popupStore.getPrice();
        this.contents = popupStore.getContents();
        this.image = popupStore.getImage();
        this.address = popupStore.getAddress();
    }


}
