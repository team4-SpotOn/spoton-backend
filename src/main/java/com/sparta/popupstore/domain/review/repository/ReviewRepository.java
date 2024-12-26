package com.sparta.popupstore.domain.review.repository;

import com.sparta.popupstore.domain.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findAllByPopupStoreId(Long popupStoreId, Pageable pageable);
}
