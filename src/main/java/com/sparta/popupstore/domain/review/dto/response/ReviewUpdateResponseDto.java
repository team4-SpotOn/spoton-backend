package com.sparta.popupstore.domain.review.dto.response;

import com.sparta.popupstore.domain.review.entity.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
public class ReviewUpdateResponseDto {

  @Schema(description = "팝업스토어 고유번호")
  private Long id;

  @Schema(description = "수정된 리뷰내용")
  private String contents;

  @Schema(description = "수정된 별점")
  private int star;

  @Schema(description = "유저이름")
  private String name;

  public ReviewUpdateResponseDto(Review review) {
    this.id = review.getId();
    this.star = review.getStar();
    this.contents = review.getContents();
    this.name =review.getUser().getName();
  }
}
