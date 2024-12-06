package com.sparta.popupstore.domain.review.entity;

import com.sparta.popupstore.domain.common.entity.BaseEntity;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "reviews")
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "popupstore_id")
    private PopupStore popupStore;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String contents;
    private int star;

    @Builder
    public Review(Long id, PopupStore popupStore, User user, String contents, int star) {
        this.id = id;
        this.popupStore = popupStore;
        this.user = user;
        this.contents = contents;
        this.star = star;
    }

    public void update(String contents, int star) {
        this.contents = contents;
        this.star = star;
    }
}
