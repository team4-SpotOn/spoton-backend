package com.sparta.popupstore.domain.popupstore.dto.request;

import com.sparta.popupstore.domain.company.entity.Company;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class PopupStoreCreateRequestDto {
    @NotNull(message = "팝업스토어 이름을 입력해주세요.")
    private String name;
    @NotNull(message = "팝업스토어 내용을 입력해주세요.")
    private String content;
    @Min(value = 0, message = "가격은 0이상이어야만 합니다.")
    private int price;
    @NotNull(message = "팝업스토어 주소를 입력해주세요.")
    private String address;
    @NotNull(message = "팝업스토어 시작일을 입력해주세요.")
    private LocalDate startDate;
    @NotNull(message = "팝업스토어 종료일을 입력해주세요.")
    private LocalDate endDate;
    @NotNull(message = "팝업스토어 개장시간을 입력해주세요.")
    private LocalTime startTime;
    @NotNull(message = "팝업스토어 폐장시간을 입력해주세요.")
    private LocalTime endTime;

    public PopupStore toEntity(Company company, String imagePath) {
        return PopupStore.builder()
                .company(company)
                .name(this.name)
                .contents(this.content)
                .image(imagePath)
                .price(this.price)
                .address(this.address)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .startTime(this.startTime)
                .endTime(this.endTime)
                .build();
    }
}
