package com.sparta.popupstore.domain.review.dto.response;

import com.sparta.popupstore.domain.review.entity.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ReviewCreateResponseDto {

  @Schema(description = "리뷰 고유번호")
  private Long id;

  @Schema(description = "리뷰내용")
  private String contents;

  @Schema(description = "별점")
  private int star;

  @Schema(description = "유저이름")
  private String name;

  @Schema(description = "imageUrl")
  private String imageUrl;

  public ReviewCreateResponseDto(Review review) {
    this.id = review.getId();
    this.star = review.getStar();
    this.contents = review.getContents();
    this.name =review.getUser().getName();
    this.imageUrl = review.getImageUrl();
  }
}
