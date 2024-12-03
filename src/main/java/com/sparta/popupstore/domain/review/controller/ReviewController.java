package com.sparta.popupstore.domain.review.controller;

import com.sparta.popupstore.Login.LoginUser;
import com.sparta.popupstore.domain.review.dto.request.ReviewSaveRequestDto;
import com.sparta.popupstore.domain.review.dto.response.ReviewSaveResponseDto;
import com.sparta.popupstore.domain.review.entity.Review;
import com.sparta.popupstore.domain.review.service.ReviewService;
import com.sparta.popupstore.domain.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/popupstores")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/{id}/reviews")
    public ResponseEntity<ReviewSaveResponseDto> createReview(@LoginUser User user,@PathVariable Long id, @RequestBody @Valid ReviewSaveRequestDto requestDto) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(reviewService.createReview(user,id, requestDto));
    }
}
