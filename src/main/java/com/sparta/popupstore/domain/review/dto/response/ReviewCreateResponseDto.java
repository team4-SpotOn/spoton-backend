package com.sparta.popupstore.domain.review.dto.response;

import com.sparta.popupstore.domain.review.entity.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ReviewCreateResponseDto {

  @Schema(description = "리뷰 고유번호")
  private final Long id;

  @Schema(description = "리뷰내용")
  private final String contents;

  @Schema(description = "별점")
  private final Integer star;

  @Schema(description = "유저이름")
  private final String name;

  @Schema(description = "imageUrl")
  private final String imageUrl;

  @Schema(description = "생성일")
  private final LocalDateTime createdAt;

  public ReviewCreateResponseDto(Review review) {
    this.id = review.getId();
    this.star = review.getStar();
    this.contents = review.getContents();
    this.name =review.getUser().getName();
    this.createdAt = review.getCreatedAt();
    this.imageUrl = review.getImageUrl();
  }
}
