package com.sparta.popupstore.domain.popupstore.entity;

import com.sparta.popupstore.domain.common.entity.Address;
import com.sparta.popupstore.domain.common.entity.BaseEntity;
import com.sparta.popupstore.domain.company.entity.Company;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "popupstores")
public class PopupStore extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    private String name;
    private String contents;
    private int price;
    private int view;
    @Embedded
    private Address address;
    private LocalDate startDate;
    private LocalDate endDate;

    @Builder
    public PopupStore(Long id, Company company, String name, String contents, int price, int view, Address address, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.company = company;
        this.name = name;
        this.contents = contents;
        this.price = price;
        this.view = view;
        this.address = address;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void update(String name, String contents, int price, Address address, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.contents = contents;
        this.price = price;
        this.address = address;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void viewPopupStore() {
        this.view += 1;
    }
}
