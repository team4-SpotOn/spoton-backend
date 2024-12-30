package com.sparta.popupstore.domain.review.service;

import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.repository.PopupStoreRepository;
import com.sparta.popupstore.domain.reservation.repository.ReservationRepository;
import com.sparta.popupstore.domain.review.dto.request.ReviewCreateRequestDto;
import com.sparta.popupstore.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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

    @Mock
    private ReservationRepository reservationRepository;

    @Test
    @DisplayName("리뷰생성 테스트")
    void createReviewTest1() {
        // given
        Long popupStoreId = popupStore.getId();
        Integer star = 5;
        String content = " ";
        ReviewCreateRequestDto requestDto = ReviewCreateRequestDto.builder()
                .user(user)
                .star(star)
                .contents(content)
                .popupStore(popupStore)
                .build();

        //when
        CustomApiException exception = assertThrows(
                CustomApiException.class,
                () -> reviewService.createReview(user, popupStoreId, requestDto)
        );

        // then
        assertEquals(ErrorCode.POPUP_STORE_NOT_FOUND.getMessage(), exception.getMessage());
    }

    @Test
    void createReviewTest2() {
        // Given
        Long popupStoreId = popupStore.getId();
        when(reservationRepository.existsByUserAndPopupStore(user, popupStore)).thenReturn(false);
        when(popupStoreRepository.findById(popupStoreId)).thenReturn(Optional.of(popupStore));
        Integer star = 5;
        String content = " ";
        ReviewCreateRequestDto requestDto = ReviewCreateRequestDto.builder()
                .user(user)
                .star(star)
                .contents(content)
                .popupStore(popupStore)
                .build();

        // When
        CustomApiException exception = assertThrows(
                CustomApiException.class,
                () -> reviewService.createReview(user, popupStoreId, requestDto)
        );

        // Then
        assertEquals(ErrorCode.POPUP_STORE_NOT_RESERVATION.getMessage(), exception.getMessage());
    }
}