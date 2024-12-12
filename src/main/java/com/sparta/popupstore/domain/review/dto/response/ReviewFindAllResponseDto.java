package com.sparta.popupstore.domain.review.dto.response;

import com.sparta.popupstore.domain.review.entity.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ReviewFindAllResponseDto {
  @Schema(description = "리뷰 고유번호")
  private final Long id;

  @Schema(description = "리뷰내용")
  private final String contents;

  @Schema(description = "별점")
  private final int star;

  @Schema(description = "유저이름")
  private final String name;

  @Schema(description = "이미지 저장한 경로")
  private final String imageUrl;

  public ReviewFindAllResponseDto(Review review) {
    this.id = review.getId();
    this.star = review.getStar();
    this.contents = review.getContents();
    this.name =review.getUser().getName();
    this.imageUrl = review.getImageUrl();
  }
}
