package com.sparta.popupstore.domain.review.dto.request;

import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.review.entity.Review;
import com.sparta.popupstore.domain.user.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewCreateRequestDto {
  private int star;
  private String content;

  public Review toEntity(User user, PopupStore popupStore) {
    return Review.builder()
        .user(user)
        .popupStore(popupStore)
        .star(this.star)
        .contents(this.content)
        .build();
  }
}
