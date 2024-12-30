package com.sparta.popupstore.domain.review.service;

import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.review.dto.request.ReviewCreateRequestDto;
import com.sparta.popupstore.domain.user.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock
    private ReviewService reviewService;

    @Mock
    private User user;

    @Mock
    private PopupStore popupStore;

    @Test
    void createReviewTest() {
        // given
        Integer star = 6;
        String contents = "Great popup!";
        Long popupStoreId = 1L;
        ReviewCreateRequestDto requestDto = ReviewCreateRequestDto.builder()
                .user(user)
                .popupStore(popupStore)
                .star(star)
                .contents(contents)
                .build();
        // when
        String exception = assertThrows(CustomApiException.class , () -> reviewService.createReview(user, popupStoreId, requestDto)).getMessage();
        // then
        assertEquals("해당 팝업스토어가 없습니다." , exception);
        assertEquals("예약한 팝업스토어가 아닙니다." , exception);
    }
}