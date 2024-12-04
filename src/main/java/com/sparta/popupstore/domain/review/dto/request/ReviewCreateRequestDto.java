package com.sparta.popupstore.domain.review.dto.request;

import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.review.entity.Review;
import com.sparta.popupstore.domain.user.entity.User;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewCreateRequestDto {
  @DecimalMax(value = "5.0", message = "1~5사이의 별점을 입력해주세요.")
  @DecimalMin(value = "1.0", message = "1~5사이의 별점을 입력해주세요.")
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
