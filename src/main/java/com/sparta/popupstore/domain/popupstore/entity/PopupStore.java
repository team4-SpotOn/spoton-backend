package com.sparta.popupstore.domain.popupstore.entity;

import com.sparta.popupstore.domain.common.entity.BaseEntity;
import com.sparta.popupstore.domain.company.entity.Company;
import com.sparta.popupstore.domain.popupstore.dto.request.PopupStoreUpdateRequestDto;
import jakarta.persistence.*;
import lombok.*;

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
    private Company company;
    private String name;
    private String contents;
    private String image;
    private int price;
    private String address; // 위도와 경도로 주소 저장.
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;


    @Builder
    public PopupStore(Long id, Company company, String name, String contents, String image, int price, String address, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
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

    public void update(PopupStoreUpdateRequestDto requestDto, String imagePath) {
        this.name = requestDto.getName() != null ? requestDto.getName() : this.name;
        this.contents = requestDto.getContent() != null ? requestDto.getContent() : this.contents;
        this.image = imagePath != null ? imagePath : requestDto.getImagePath();
        this.price = requestDto.getPrice() != null ? Integer.parseInt(requestDto.getPrice()) : this.price;
        this.address = requestDto.getAddress() != null ? requestDto.getAddress() : this.address;
        this.startDate = requestDto.getStartDate() != null ? requestDto.getStartDate() : this.startDate;
        this.endDate = requestDto.getEndDate() != null ? requestDto.getEndDate() : this.endDate;
        this.startTime = requestDto.getStartTime() != null ? LocalTime.from(requestDto.getStartTime()) : this.startTime;
        this.endTime = requestDto.getEndTime() != null ? LocalTime.from(requestDto.getEndTime()) : this.endTime;
    }
}
