package com.sparta.popupstore.domain.popupstore.dto.request;

import com.sparta.popupstore.domain.common.entity.Address;
import com.sparta.popupstore.domain.company.entity.Company;
import com.sparta.popupstore.domain.popupstore.bundle.dto.request.PopupStoreAttributeRequestDto;
import com.sparta.popupstore.domain.popupstore.bundle.dto.request.PopupStoreImageRequestDto;
import com.sparta.popupstore.domain.popupstore.bundle.dto.request.PopupStoreOperatingRequestDto;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class PopupStoreCreateRequestDto {
    @NotNull(message = "팝업스토어 이름을 입력해주세요.")
    private String name;
    @NotNull(message = "팝업스토어 내용을 입력해주세요.")
    private String contents;
    @Min(value = 0, message = "가격은 0이상이어야만 합니다.")
    private Integer price;
    @NotNull(message = "예약 가능 인원을 입력해주세요.")
    @Positive(message = "예약 가능 인원은 자연수 여야 합니다.")
    private Integer reservationLimit;
    @NotNull(message = "팝업스토어 주소를 입력해주세요.")
    private String address;
    @NotNull(message = "팝업스토어 시작일을 입력해주세요.")
    private LocalDate startDate;
    @NotNull(message = "팝업스토어 종료일을 입력해주세요.")
    private LocalDate endDate;
    @NotEmpty(message = "하나 이상의 이미지를 넣어주세요")
    private List<PopupStoreImageRequestDto> imageList;
    @NotEmpty(message = "팝업스토어 영업시간을 입력해주세요.")
    private List<PopupStoreOperatingRequestDto> operatingList;
    @NotEmpty(message = "하나 이상의 속성을 넣어주세요")
    private List<PopupStoreAttributeRequestDto> attributeList;

    public PopupStore toEntity(Company company, Address address) {
        return PopupStore.builder()
                .company(company)
                .thumbnail(this.imageList.get(0).getImageUrl())
                .name(this.name)
                .contents(this.contents)
                .price(this.price)
                .reservationLimit(this.reservationLimit)
                .view(0)
                .address(address)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .build();
    }
}
