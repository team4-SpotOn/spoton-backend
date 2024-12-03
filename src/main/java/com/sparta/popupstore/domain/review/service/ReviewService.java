package com.sparta.popupstore.domain.review.service;

import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.repository.PopupStoreRepository;
import com.sparta.popupstore.domain.review.dto.request.ReviewSaveRequestDto;
import com.sparta.popupstore.domain.review.dto.response.ReviewSaveResponseDto;
import com.sparta.popupstore.domain.review.entity.Review;
import com.sparta.popupstore.domain.review.repository.ReviewRepository;
import com.sparta.popupstore.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final PopupStoreRepository popupStoreRepository;

    public ReviewSaveResponseDto createReview(User user,Long id , ReviewSaveRequestDto requestDto) {
        PopupStore popupStore = popupStoreRepository.findById(id).orElseThrow();
        Review review = requestDto.toReviewSaveRequestDto(user, id);
        reviewRepository.save(review);
        return ReviewSaveResponseDto.createResponseDto(user, review);
    }
}
