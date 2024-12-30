package com.sparta.popupstore.domain.review.dto.request;

import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.review.entity.Review;
import com.sparta.popupstore.domain.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

@Getter
@Builder
public class ReviewCreateRequestDto {

    @Range(min = 1, max = 5, message = "1~5사이의 별점을 입력해주세요.")
    private Integer star;

    @NotBlank(message = "내용을 입력하세요")
    private String contents;

    private String imageUrl;

    private User user;
    public PopupStore popupStore;

    public Review toEntity(User user, PopupStore popupStore) {
        return Review.builder()
                .user(user)
                .popupStore(popupStore)
                .star(star)
                .contents(contents)
                .imageUrl(imageUrl)
                .build();
    }
}
