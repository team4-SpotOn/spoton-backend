package com.sparta.popupstore.domain.popupstore.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "popupstore_attributes")
public class PopupStoreAttributes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @Setter
    @JoinColumn(name = "popupstore_id")
    private PopupStore popupStore;

    @Enumerated(EnumType.STRING)
    private PopupStoreAttribute attribute;
    private Boolean isAllow;

    public PopupStoreAttributes(PopupStore popupStore, PopupStoreAttribute attribute, Boolean isAllow) {
        this.popupStore = popupStore;
        this.attribute = attribute;
        this.isAllow = isAllow;
    }

}
