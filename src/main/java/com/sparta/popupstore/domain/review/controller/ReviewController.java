package com.sparta.popupstore.domain.review.controller;

import com.sparta.popupstore.domain.common.annotation.AuthUser;
import com.sparta.popupstore.domain.review.dto.request.ReviewCreateRequestDto;
import com.sparta.popupstore.domain.review.dto.request.ReviewUpdateRequestDto;
import com.sparta.popupstore.domain.review.dto.response.ReviewCreateResponseDto;
import com.sparta.popupstore.domain.review.dto.response.ReviewFindAllResponseDto;
import com.sparta.popupstore.domain.review.dto.response.ReviewUpdateResponseDto;
import com.sparta.popupstore.domain.review.service.ReviewService;
import com.sparta.popupstore.domain.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
@Tag(name = "리뷰 API", description = "리뷰 CRUD API")
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "리뷰생성")
    @Parameter(name = "popupStoreId", description = "리뷰 할 팝업스토어의 기본키")
    @Parameter(name = "contents", description = "리뷰내용")
    @Parameter(name = "star", description = "별점")
    @Parameter(name = "user", description = "로그인 한 유저")
    @Parameter(name = "imageUrl", description = "이미지 저장된 경로")
    @PostMapping("/popupstores/{popupStoreId}")
    public ResponseEntity<ReviewCreateResponseDto> createReview(
            @AuthUser User user,
            @PathVariable Long popupStoreId,
            @RequestBody ReviewCreateRequestDto requestDto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(reviewService.createReview(user, popupStoreId, requestDto));
    }

    @Operation(summary = "리뷰수정")
    @Parameter(name = "reviewId", description = "수정할 리뷰의 기본키")
    @Parameter(name = "contents", description = "수정된 리뷰내용")
    @Parameter(name = "star", description = "수정된 별점")
    @Parameter(name = "user", description = "로그인 한 유저")
    @Parameter(name = "imageUrl", description = "이미지 저장된 경로")
    @PatchMapping("/{reviewId}")
    public ResponseEntity<ReviewUpdateResponseDto> updateReview(
            @AuthUser User user,
            @PathVariable Long reviewId,
            @RequestBody ReviewUpdateRequestDto updateRequestDto
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reviewService.updateReview(user, reviewId, updateRequestDto));
    }

    @Operation(summary = "리뷰삭제")
    @Parameter(name = "user", description = "로그인 한 유저")
    @Parameter(name = "reviewId", description = "삭제할 리뷰의 기본키")
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(
            @AuthUser User user,
            @PathVariable Long reviewId
    ) {
        reviewService.deleteReview(user, reviewId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Operation(summary = "리뷰 조회")
    @Parameter(name = "popupStoreId", description = "조회할 팝업스토어의 기본키")
    @Parameter(name = "name", description = "유저이름")
    @Parameter(name = "page", description = "페이지 번호")
    @Parameter(name = "size", description = "페이지 사이즈")
    @GetMapping("/popupstores/{popupStoreId}")
    public ResponseEntity<Page<ReviewFindAllResponseDto>> findReviews(
            @PathVariable Long popupStoreId,
            @PageableDefault(sort = "updatedAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reviewService.findReview(popupStoreId, pageable));
    }
}
