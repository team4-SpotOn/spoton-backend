package com.sparta.popupstore.domain.popupstore.dto.request;

import com.sparta.popupstore.domain.popupstore.bundle.dto.request.PopupStoreAttributeRequestDto;
import com.sparta.popupstore.domain.popupstore.bundle.dto.request.PopupStoreImageRequestDto;
import com.sparta.popupstore.domain.popupstore.bundle.dto.request.PopupStoreOperatingRequestDto;
import jakarta.validation.constraints.*;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Getter
public class PopupStoreUpdateRequestDto {
    @NotBlank(message = "팝업스토어 이름을 입력해주세요.")
    private String name;
    @NotNull(message = "팝업스토어 시작날짜를 입력해주세요.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @NotNull(message = "팝업스토어 종료날짜 입력해주세요.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    @Min(value = 0, message = "가격은 0이상이어야만 합니다.")
    private Integer price;
    @NotNull(message = "예약 가능 인원을 입력해주세요.")
    @Positive(message = "예약 가능 인원은 자연수 여야 합니다.")
    private Integer reservationLimit;
    @NotBlank(message = "팝업스토어 내용을 입력해주세요.")
    private String contents;
    @NotBlank(message = "팝업스토어 주소를 입력해주세요.")
    private String address;
    @NotEmpty(message = "하나 이상의 이미지를 넣어주세요")
    private List<PopupStoreImageRequestDto> imageList;
    @NotEmpty(message = "팝업스토어 영업시간을 입력해주세요.")
    private List<PopupStoreOperatingRequestDto> operatingList;
    @NotEmpty(message = "하나 이상의 속성을 넣어주세요")
    private List<PopupStoreAttributeRequestDto> attributeList; // 속성 추가
}
