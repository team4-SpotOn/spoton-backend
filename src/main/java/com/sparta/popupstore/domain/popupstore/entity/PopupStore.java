package com.sparta.popupstore.domain.popupstore.entity;

import com.sparta.popupstore.domain.common.entity.BaseEntity;
import com.sparta.popupstore.domain.company.entity.Company;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "popupstores")
public class PopupStore extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id")
    @NotNull(message = "회사를 입력해주세요.")
    private Company company;

    @NotNull(message = "팝업스토어 이름을 입력해주세요.")
    private String name;
    @NotNull(message = "팝업스토어 내용을 입력해주세요.")
    private String contents;
    @NotNull(message = "팝업스토어 이미지를 추가해주세요.")
    private String image;
    @Min(value = 0, message = "가격은 0이상이어야만 합니다.")
    private int price;
    @NotNull(message = "팝업스토어 주소 입력해주세요.")
    private String address; // 위도와 경도로 주소 저장.
    @NotNull(message = "팝업스토어 시작일 입력해주세요.")
    private LocalDate startDate;
    @NotNull(message = "팝업스토어 종료일 입력해주세요.")
    private LocalDate endDate;
    @NotNull(message = "팝업스토어 개장시간 입력해주세요.")
    private LocalTime startTime;
    @NotNull(message = "팝업스토어 폐장시간 입력해주세요.")
    private LocalTime endTime;


    @Builder
    public PopupStore(Long id, Company company, String name,  String contents, String image, int price, String address, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        this.id = id;
        this.company = company;
        this.name = name;
        this.contents = contents;
        this.image = image;
        this.price = price;
        this.address = address;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
