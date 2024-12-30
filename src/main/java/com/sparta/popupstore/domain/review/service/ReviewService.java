package com.sparta.popupstore.domain.review.service;

import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.repository.PopupStoreRepository;
import com.sparta.popupstore.domain.reservation.repository.ReservationRepository;
import com.sparta.popupstore.domain.review.dto.request.ReviewCreateRequestDto;
import com.sparta.popupstore.domain.review.dto.request.ReviewUpdateRequestDto;
import com.sparta.popupstore.domain.review.dto.response.ReviewCreateResponseDto;
import com.sparta.popupstore.domain.review.dto.response.ReviewFindAllResponseDto;
import com.sparta.popupstore.domain.review.dto.response.ReviewUpdateResponseDto;
import com.sparta.popupstore.domain.review.entity.Review;
import com.sparta.popupstore.domain.review.repository.ReviewRepository;
import com.sparta.popupstore.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final PopupStoreRepository popupStoreRepository;
    private final ReservationRepository reservationRepository;

    public ReviewCreateResponseDto createReview(User user, Long popupStoreId, ReviewCreateRequestDto requestDto) {
        PopupStore popupStore = popupStoreRepository.findById(popupStoreId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.POPUP_STORE_NOT_FOUND));
        if(!reservationRepository.existsByUserAndPopupStore(user, popupStore)) {
            throw new CustomApiException(ErrorCode.POPUP_STORE_NOT_RESERVATION);
        }
        Review review = requestDto.toEntity();
        return new ReviewCreateResponseDto(reviewRepository.save(review));
    }

    @Transactional
    public ReviewUpdateResponseDto updateReview(User user, Long reviewId, ReviewUpdateRequestDto requestDto) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.REVIEW_NOT_FOUND));
        if(!review.getUser().getId().equals(user.getId())) {
            throw new CustomApiException(ErrorCode.REVIEW_NOT_UPDATE);
        }
        review.update(
                requestDto.getContents(),
                requestDto.getStar(),
                requestDto.getImageUrl()
        );
        return new ReviewUpdateResponseDto(review);
    }

    public void deleteReview(User user, Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.REVIEW_NOT_FOUND));
        if(!review.getUser().getId().equals(user.getId())) {
            throw new CustomApiException(ErrorCode.REVIEW_CANT_DELETE);
        }
        reviewRepository.delete(review);
    }

    public Page<ReviewFindAllResponseDto> findAllReviews(Long popupStoreId, Pageable pageable) {
        return reviewRepository.findAllByPopupStoreId(popupStoreId, pageable).map(ReviewFindAllResponseDto::new);
    }
}
