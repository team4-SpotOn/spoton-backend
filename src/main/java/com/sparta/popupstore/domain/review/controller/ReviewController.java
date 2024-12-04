package com.sparta.popupstore.domain.review.controller;

import com.sparta.popupstore.domain.common.annotation.AuthUser;
import com.sparta.popupstore.domain.review.dto.request.ReviewCreateRequestDto;
import com.sparta.popupstore.domain.review.dto.request.ReviewUpdateRequestDto;
import com.sparta.popupstore.domain.review.dto.response.ReviewCreateResponseDto;
import com.sparta.popupstore.domain.review.dto.response.ReviewUpdateResponseDto;
import com.sparta.popupstore.domain.review.service.ReviewService;
import com.sparta.popupstore.domain.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Review", description = "리뷰 관련 API")
public class ReviewController {

    private final ReviewService reviewService;


    @Operation(summary = "리뷰생성")
    @Parameter(name = "id", description = "팝업스토어 고유번호")
    @Parameter(name = "contents", description = "리뷰내용")
    @Parameter(name = "star", description = "별점")
    @Parameter(name = "name", description = "유저이름")
    @PostMapping("/{popupStoreId}")
    public ResponseEntity<ReviewCreateResponseDto> createReview(@AuthUser User user ,@PathVariable Long popupStoreId, @RequestBody ReviewCreateRequestDto requestDto) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(reviewService.createReview(user,popupStoreId, requestDto));
    }

    @Operation(summary = "리뷰생성")
    @Parameter(name = "id", description = "팝업스토어 고유번호")
    @Parameter(name = "contents", description = "수정된 리뷰내용")
    @Parameter(name = "star", description = "수정된 별점")
    @Parameter(name = "name", description = "유저이름")
    @PatchMapping("/{reviewId}")
    public ResponseEntity<ReviewUpdateResponseDto> updateReview(@AuthUser User user ,@PathVariable Long reviewId, @RequestBody ReviewUpdateRequestDto updateRequestDto) {
        return ResponseEntity.ok(reviewService.updateReview(user, reviewId, updateRequestDto));
    }

    @Operation(summary = "리뷰삭제")
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@AuthUser User user ,
        @PathVariable Long reviewId) {
        reviewService.deleteReview(user, reviewId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
