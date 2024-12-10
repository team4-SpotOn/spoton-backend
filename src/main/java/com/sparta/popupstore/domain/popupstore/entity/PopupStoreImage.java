package com.sparta.popupstore.domain.popupstore.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "popupstore_images")
public class PopupStoreImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "popupstore_id")
    private PopupStore popupStore;
    private String imageUrl;
    private int sort;

    @Builder
    public PopupStoreImage(String imageUrl, int sort) {
        this.imageUrl = imageUrl;
        this.sort = sort;
    }

    public void addPopupStore(PopupStore popupStore) {
        this.popupStore = popupStore;
    }
}
