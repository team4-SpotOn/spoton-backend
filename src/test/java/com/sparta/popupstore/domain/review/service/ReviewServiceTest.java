package com.sparta.popupstore.domain.review.service;

import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.repository.PopupStoreRepository;
import com.sparta.popupstore.domain.reservation.repository.ReservationRepository;
import com.sparta.popupstore.domain.review.dto.request.ReviewCreateRequestDto;
import com.sparta.popupstore.domain.user.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private PopupStoreRepository popupStoreRepository;

    @Mock
    private User user;

    @Mock
    private PopupStore popupStore;

    @Test
    void createReviewTest() {
        // given
        Integer star = 5;
        String contents = "최고";
        Long popupStoreId = popupStore.getId();
        ReviewCreateRequestDto requestDto = ReviewCreateRequestDto.builder()
                .star(star)
                .contents(contents)
                .build();

        // when
        CustomApiException exception = assertThrows(
                CustomApiException.class,
                () -> reviewService.createReview(user, popupStoreId, requestDto)
        );

        // then
        assertEquals(ErrorCode.POPUP_STORE_NOT_FOUND.getMessage(), exception.getMessage());
    }
}