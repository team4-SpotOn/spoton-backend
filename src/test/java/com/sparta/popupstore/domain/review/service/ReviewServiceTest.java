package com.sparta.popupstore.domain.review.service;

import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.repository.PopupStoreRepository;
import com.sparta.popupstore.domain.review.dto.request.ReviewCreateRequestDto;
import com.sparta.popupstore.domain.user.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;

    @MockBean
    private PopupStoreRepository popupStoreRepository;

    @MockBean
    private User user;

    @MockBean
    private PopupStore popupStore;

    @Test
    void createReviewTest() {
        // given
        Long popupStoreId = popupStore.getId();
        ReviewCreateRequestDto requestDto = ReviewCreateRequestDto.builder()
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
}