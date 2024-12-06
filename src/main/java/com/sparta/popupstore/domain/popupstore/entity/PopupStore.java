package com.sparta.popupstore.domain.popupstore.entity;

import com.sparta.popupstore.domain.common.entity.Address;
import com.sparta.popupstore.domain.common.entity.BaseEntity;
import com.sparta.popupstore.domain.company.entity.Company;
import com.sparta.popupstore.domain.popupstore.dto.request.PopupStoreUpdateRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "popupStore", cascade = CascadeType.ALL)
    private List<PopupStoreImage> popupStoreImageList = new ArrayList<>();
    private String name;
    private String contents;
    private int price;
    @Embedded
    private Address address;
    private LocalDate startDate;
    private LocalDate endDate;


    @Builder
    public PopupStore(Long id, Company company, String name, String contents, int price, String address, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.company = company;
        this.name = name;
        this.contents = contents;
        this.price = price;
        this.address = new Address(address);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void update(PopupStoreUpdateRequestDto requestDto) {
        this.name = requestDto.getName() != null ? requestDto.getName() : this.name;
        this.contents = requestDto.getContent() != null ? requestDto.getContent() : this.contents;
        this.price = requestDto.getPrice() != null ? Integer.parseInt(requestDto.getPrice()) : this.price;
        //this.address = address update
        this.startDate = requestDto.getStartDate() != null ? requestDto.getStartDate() : this.startDate;
        this.endDate = requestDto.getEndDate() != null ? requestDto.getEndDate() : this.endDate;
    }
}
