package com.sparta.popupstore.domain.popupstore.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "popupstore_attributes")
public class PopupStoreAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "popupstore_id")
    private PopupStore popupStore;

    @Enumerated(EnumType.STRING)
    private PopupStoreAttributeEnum attribute;
    private Boolean isAllow;

    public PopupStoreAttribute(PopupStore popupStore, PopupStoreAttributeEnum attribute, Boolean isAllow) {
        this.popupStore = popupStore;
        this.attribute = attribute;
        this.isAllow = isAllow;
    }
}