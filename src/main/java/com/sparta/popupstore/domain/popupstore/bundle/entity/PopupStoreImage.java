package com.sparta.popupstore.domain.popupstore.bundle.entity;


import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
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
    private Integer sort;

    @Builder
    public PopupStoreImage(Long id, PopupStore popupStore, String imageUrl, Integer sort) {
        this.id = id;
        this.popupStore = popupStore;
        this.imageUrl = imageUrl;
        this.sort = sort;
    }
}
