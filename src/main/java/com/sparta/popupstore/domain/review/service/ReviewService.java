package com.sparta.popupstore.domain.review.service;

import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.repository.PopupStoreRepository;
import com.sparta.popupstore.domain.review.dto.request.ReviewCreateRequestDto;
import com.sparta.popupstore.domain.review.dto.request.ReviewUpdateRequestDto;
import com.sparta.popupstore.domain.review.dto.response.ReviewCreateResponseDto;
import com.sparta.popupstore.domain.review.dto.response.ReviewFindAllResponseDto;
import com.sparta.popupstore.domain.review.dto.response.ReviewUpdateResponseDto;
import com.sparta.popupstore.domain.review.entity.Review;
import com.sparta.popupstore.domain.review.repository.ReviewRepository;
import com.sparta.popupstore.domain.user.entity.User;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final PopupStoreRepository popupStoreRepository;

    public ReviewCreateResponseDto createReview(User user,Long popupStoreId, ReviewCreateRequestDto requestDto) {
        PopupStore popupStore = popupStoreRepository.findById(popupStoreId)
            .orElseThrow(() -> new EntityNotFoundException("해당 팝업스토어가 없습니다."));
        Review review = requestDto.toEntity(user, popupStore);
        review = reviewRepository.save(review);
        return new ReviewCreateResponseDto(review);
    }

    @Transactional
    public ReviewUpdateResponseDto updateReview(User user, Long reviewId, ReviewUpdateRequestDto updateRequestDto) {
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new EntityNotFoundException("수정할 리뷰가 없습니다."));

        if (!review.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("작성자만 수정할수있습니다.");
        }
        review.update(
            updateRequestDto.getContents(),
            updateRequestDto.getStar()
        );
        return new ReviewUpdateResponseDto(review);
    }

    public void deleteReview(User user, Long reviewId){
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new EntityNotFoundException("이미 삭제된 리뷰입니다."));
        if (!review.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("댓글을 삭제할수 없습니다.");
        }
        reviewRepository.delete(review);
    }

    public Page<ReviewFindAllResponseDto> findReview(Long popupStoreId, Pageable pageable) {
        return reviewRepository.findByPopupStoreId(popupStoreId, pageable)
            .map(ReviewFindAllResponseDto::new);
    }
}