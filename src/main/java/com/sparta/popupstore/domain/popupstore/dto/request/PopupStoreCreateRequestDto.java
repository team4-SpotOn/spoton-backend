package com.sparta.popupstore.domain.popupstore.dto.request;

import com.sparta.popupstore.domain.company.entity.Company;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

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
    private HashMap<String, LocalTime> startTimes;
    @NotNull(message = "팝업스토어 폐장시간을 입력해주세요.")
    private HashMap<String, LocalTime> endTimes;
    @Valid
    @NotEmpty(message = "하나 이상의 이미지를 넣어주세요")
    private List<PopupStoreImageRequestDto> images;

    public PopupStore toEntity(Company company) {
        return PopupStore.builder()
                .company(company)
                .name(this.name)
                .contents(this.content)
                .price(this.price)
                .address(this.address)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .build();
    }
}
