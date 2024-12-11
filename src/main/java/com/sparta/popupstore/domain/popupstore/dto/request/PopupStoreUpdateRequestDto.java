package com.sparta.popupstore.domain.popupstore.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class PopupStoreUpdateRequestDto {
    @NotBlank(message = "팝업스토어 이름을 입력해주세요.")
    private String name;
    @NotBlank(message = "팝업스토어 시작날짜를 입력해주세요.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @NotBlank(message = "팝업스토어 종료날짜 입력해주세요.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    @NotBlank(message = "팝업스토어 개장시간 입력해주세요.")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;
    @NotBlank(message = "팝업스토어 폐장시간 입력해주세요.")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime endTime;
    @Min(value = 0, message = "가격은 0이상이어야만 합니다.")
    private String price;
    @NotBlank(message = "팝업스토어 내용을 입력해주세요.")
    private String content;
    private String imageUrl;
    @NotBlank(message = "팝업스토어 주소를 입력해주세요.")
    private String address;
}
