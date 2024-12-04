package com.sparta.popupstore.domain.review.service;

import com.sparta.popupstore.domain.popupstore.repository.PopupStoreRepository;
import com.sparta.popupstore.domain.review.dto.response.ReviewCreateResponseDto;
import com.sparta.popupstore.domain.review.entity.Review;
import com.sparta.popupstore.domain.review.repository.ReviewRepository;
import com.sparta.popupstore.domain.user.entity.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final PopupStoreRepository popupStoreRepository;

    public ReviewCreateResponseDto deleteReview(User user, Long reviewId){
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 리뷰입니다."));
        reviewRepository.delete(review);
        return new ReviewCreateResponseDto(review);
    }
}
