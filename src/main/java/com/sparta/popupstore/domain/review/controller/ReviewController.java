package com.sparta.popupstore.domain.review.controller;

import com.sparta.popupstore.domain.review.dto.request.ReviewCreateRequestDto;
import com.sparta.popupstore.domain.review.dto.response.ReviewCreateResponseDto;
import com.sparta.popupstore.domain.review.service.ReviewService;
import com.sparta.popupstore.domain.user.entity.User;
import com.sparta.popupstore.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final UserRepository userRepository;

    @PostMapping("/reviews/popupstores/{id}")
    public ResponseEntity<ReviewCreateResponseDto> createReview(@PathVariable Long id, @RequestBody ReviewCreateRequestDto requestDto) {
        User user = userRepository.findById(1L).orElseThrow();
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(reviewService.createReview(user,id, requestDto));
    }
}
