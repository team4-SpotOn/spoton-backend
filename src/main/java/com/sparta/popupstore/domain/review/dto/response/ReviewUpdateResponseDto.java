package com.sparta.popupstore.domain.review.dto.response;

import com.sparta.popupstore.domain.review.entity.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ReviewUpdateResponseDto {

  @Schema(description = "리뷰 고유번호")
  private final Long id;

  @Schema(description = "수정된 리뷰내용")
  private final String contents;

  @Schema(description = "수정된 별점")
  private final Integer star;

  @Schema(description = "유저이름")
  private final String name;

  @Schema(description = "수정일")
  private final LocalDateTime updatedAt;

  @Schema(description = "수정된 이미지 저장한 경로")
  private final String imageUrl;

  public ReviewUpdateResponseDto(Review review) {
    this.id = review.getId();
    this.star = review.getStar();
    this.contents = review.getContents();
    this.name =review.getUser().getName();
    this.updatedAt = review.getUpdatedAt();
    this.imageUrl = review.getImageUrl();
  }
}
