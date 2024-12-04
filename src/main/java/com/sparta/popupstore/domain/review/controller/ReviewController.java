package com.sparta.popupstore.domain.review.controller;

import com.sparta.popupstore.domain.common.annotation.AuthUser;
import com.sparta.popupstore.domain.review.dto.request.ReviewCreateRequestDto;
import com.sparta.popupstore.domain.review.dto.request.ReviewUpdateRequestDto;
import com.sparta.popupstore.domain.review.dto.response.ReviewCreateResponseDto;
import com.sparta.popupstore.domain.review.entity.Review;
import com.sparta.popupstore.domain.review.service.ReviewService;
import com.sparta.popupstore.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reviews/popupstores")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/{popupStoresId}")
    public ResponseEntity<ReviewCreateResponseDto> createReview(@AuthUser User user ,@PathVariable Long popupStoresId, @RequestBody ReviewCreateRequestDto requestDto) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(reviewService.createReview(user,popupStoresId, requestDto));
    }

    @PatchMapping("/{reviewId}")
    public ResponseEntity<Review> updateReview(@AuthUser User user ,@PathVariable Long reviewId, @RequestBody ReviewUpdateRequestDto updateRequestDto) {
        reviewService.updateReview(user, reviewId, updateRequestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@AuthUser User user ,
        @PathVariable Long reviewId) {
        reviewService.deleteReview(user, reviewId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
