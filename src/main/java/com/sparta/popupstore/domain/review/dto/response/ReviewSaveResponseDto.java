package com.sparta.popupstore.domain.review.dto.response;

import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.review.entity.Review;
import com.sparta.popupstore.domain.user.entity.User;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewSaveResponseDto {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String contents;
  private int star;
  private String nickname;

  public static ReviewSaveResponseDto createResponseDto(User user, Review review) {
    ReviewSaveResponseDto responseDto = new ReviewSaveResponseDto();
    responseDto.id = review.getId();
    responseDto.star = review.getStar();
    responseDto.contents = review.getContents();
    responseDto.nickname = user.getNickname();
    return responseDto;
  }
}
