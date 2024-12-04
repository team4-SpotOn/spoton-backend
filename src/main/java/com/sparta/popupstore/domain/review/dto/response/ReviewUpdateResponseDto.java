package com.sparta.popupstore.domain.review.dto.response;

import com.sparta.popupstore.domain.review.entity.Review;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewUpdateResponseDto {
  private Long id;
  private String contents;
  private int star;
  private String name;

  public ReviewUpdateResponseDto(Review review) {
    this.id = review.getId();
    this.star = review.getStar();
    this.contents = review.getContents();
    this.name =review.getUser().getName();
  }
}
