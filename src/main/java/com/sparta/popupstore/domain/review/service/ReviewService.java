package com.sparta.popupstore.domain.review.service;

import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.repository.PopupStoreRepository;
import com.sparta.popupstore.domain.review.dto.request.ReviewCreateRequestDto;
import com.sparta.popupstore.domain.review.dto.response.ReviewCreateResponseDto;
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

    public ReviewCreateResponseDto createReview(User user,Long id , ReviewCreateRequestDto requestDto) {
        PopupStore popupStore = popupStoreRepository.findById(id).orElseThrow();
        Review review = requestDto.toEntity(user, popupStore);
        reviewRepository.save(review);
        return new ReviewCreateResponseDto(review);
    }
}
