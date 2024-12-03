package com.sparta.popupstore.domain.review.dto.request;

import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.review.entity.Review;
import com.sparta.popupstore.domain.user.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewSaveRequestDto {
  private int star;
  private String content;

  public Review toReviewSaveRequestDto(User user, Long id) {
    return Review.builder()
        .user(user)
        .popupStore(PopupStore.builder().id(id).build())
        .star(star)
        .contents(content)
        .build();
  }
}
