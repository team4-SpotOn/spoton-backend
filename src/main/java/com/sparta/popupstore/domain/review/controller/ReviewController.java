package com.sparta.popupstore.domain.review.controller;

import com.sparta.popupstore.domain.common.annotation.AuthUser;
import com.sparta.popupstore.domain.review.dto.request.ReviewCreateRequestDto;
import com.sparta.popupstore.domain.review.dto.response.ReviewCreateResponseDto;
import com.sparta.popupstore.domain.review.entity.Review;
import com.sparta.popupstore.domain.review.service.ReviewService;
import com.sparta.popupstore.domain.user.entity.User;
import com.sparta.popupstore.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reviews/popupstores")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final UserRepository userRepository;

    @PostMapping("/{popupstoresid}")
    public ResponseEntity<ReviewCreateResponseDto> createReview(@PathVariable Long popupstoresid, @RequestBody ReviewCreateRequestDto requestDto) {
        User user = userRepository.findById(1L).orElseThrow();
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(reviewService.createReview(user,popupstoresid, requestDto));
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Review> UpdateReview(@AuthUser User user ,@PathVariable Long reviewId, @RequestBody ReviewCreateRequestDto requestDto) {
        user = userRepository.findById(1L).orElseThrow();
        reviewService.updateReview(user, reviewId, requestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ReviewCreateResponseDto> deleteReview(@AuthUser User user ,
        @PathVariable Long reviewId) {
        user = userRepository.findById(1L).orElseThrow();
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(reviewService.deleteReview(user, reviewId));
    }

}
