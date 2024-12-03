package com.sparta.popupstore.domain.review.dto.response;

import com.sparta.popupstore.domain.review.entity.Review;
import com.sparta.popupstore.domain.user.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewSaveResponseDto {

  private Long id;
  private String contents;
  private int star;
  private String name;

  public static ReviewSaveResponseDto createResponseDto(User user, Review review) {
    ReviewSaveResponseDto responseDto = new ReviewSaveResponseDto();
    responseDto.id = review.getId();
    responseDto.star = review.getStar();
    responseDto.contents = review.getContents();
    responseDto.name = user.getName();
    return responseDto;
  }
}
