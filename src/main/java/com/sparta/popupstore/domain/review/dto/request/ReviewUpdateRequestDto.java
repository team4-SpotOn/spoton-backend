package com.sparta.popupstore.domain.review.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

@Getter
public class ReviewUpdateRequestDto {

  @Range(min = 1, max = 5, message = "1~5사이의 별점을 입력해주세요.")
  private int star;

  @NotBlank(message = "내용을 입력하세요")
  private String contents;
}