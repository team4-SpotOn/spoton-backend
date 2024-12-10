package com.sparta.popupstore.domain.popupstore.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.HashMap;

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
    @NotNull(message = "팝업스토어 개장시간을 입력해주세요.")
    private HashMap<String, LocalTime> startTimes;
    @NotNull(message = "팝업스토어 폐장시간을 입력해주세요.")
    private HashMap<String, LocalTime> endTimes;
    @Min(value = 0, message = "가격은 0이상이어야만 합니다.")
    private String price;
    @NotBlank(message = "팝업스토어 내용을 입력해주세요.")
    private String content;
    @NotBlank(message = "팝업스토어 주소를 입력해주세요.")
    private String address;
    @Valid
    @NotEmpty(message = "하나 이상의 이미지를 넣어주세요")
    private List<PopupStoreImageRequestDto> images;
}
