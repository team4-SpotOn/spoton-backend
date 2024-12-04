package com.sparta.popupstore.domain.review.dto.response;

import com.sparta.popupstore.domain.review.entity.Review;
import com.sparta.popupstore.domain.user.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewCreateResponseDto {

  private Long id;
  private String contents;
  private int star;
  private String name;

  public ReviewCreateResponseDto(Review review) {
    this.id = review.getId();
    this.star = review.getStar();
    this.contents = review.getContents();
    this.name =review.getUser().getName();
  }
}
