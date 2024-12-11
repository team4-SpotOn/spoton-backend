package com.sparta.popupstore.domain.promotionevent.service;

import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.domain.common.util.ValidUtil;
import com.sparta.popupstore.domain.popupstore.repository.PopupStoreRepository;
import com.sparta.popupstore.domain.promotionevent.dto.request.PromotionEventCreateRequestDto;
import com.sparta.popupstore.domain.promotionevent.dto.request.PromotionEventUpdateRequestDto;
import com.sparta.popupstore.domain.promotionevent.dto.response.*;
import com.sparta.popupstore.domain.promotionevent.entity.Coupon;
import com.sparta.popupstore.domain.promotionevent.entity.PromotionEvent;
import com.sparta.popupstore.domain.promotionevent.repository.PromotionEventRepository;
import com.sparta.popupstore.domain.user.entity.User;
import com.sparta.popupstore.s3.S3ImageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PromotionEventService {

    private final PromotionEventRepository promotionEventRepository;
    private final PopupStoreRepository popupStoreRepository;
    private final CouponService couponService;
    private final S3ImageService s3ImageService;

    public PromotionEventCreateResponseDto createEvent(
            PromotionEventCreateRequestDto createRequestDto,
            Long popupStoreId
    ) {
        PromotionEvent promotionEvent = createRequestDto.toEvent();
        if(popupStoreId != null) {
            popupStoreRepository.findById(popupStoreId).ifPresent(promotionEvent::addPopupStore);
        }
        return new PromotionEventCreateResponseDto(promotionEventRepository.save(promotionEvent));
    }

    public Page<PromotionEventFindAllResponseDto> findAllPromotionalEvents(int page, int size) {
        Pageable pageable = PageRequest.of(page-1, size);
        return promotionEventRepository.findAllPromotionalEvents(pageable).map(PromotionEventFindAllResponseDto::new);
    }

    public PromotionEventFindOneResponseDto findOnePromotionEvent(Long promotionEventId) {
        return new PromotionEventFindOneResponseDto(this.getPromotionEvent(promotionEventId));
    }

    @Transactional
    public PromotionEventUpdateResponseDto updatePromotionEvent(
            PromotionEventUpdateRequestDto updateRequestDto,
            Long promotionEventId
    ) {
        PromotionEvent promotionEvent = this.getPromotionEvent(promotionEventId);
        if(promotionEvent.getStartDateTime().isBefore(LocalDateTime.now())){
            throw new CustomApiException(ErrorCode.PROMOTION_EVENT_ALREADY);
        }
        if(ValidUtil.isValidNullAndEmpty(promotionEvent.getImageUrl()) && !Objects.equals(updateRequestDto.getImageUrl(), promotionEvent.getImageUrl())){
            s3ImageService.deleteImage(promotionEvent.getImageUrl());
        }
        promotionEvent.updatePromotionEvent(
                updateRequestDto.getTitle(),
                updateRequestDto.getDescription(),
                updateRequestDto.getDiscountPercentage(),
                updateRequestDto.getTotalCount(),
                updateRequestDto.getCouponExpirationPeriod(),
                updateRequestDto.getStartDateTime(),
                updateRequestDto.getEndDateTime(),
                updateRequestDto.getImageUrl()
        );
        return new PromotionEventUpdateResponseDto(promotionEvent);
    }

    @Transactional
    public void deletePromotionEvent(Long promotionEventId) {
        PromotionEvent promotionEvent = this.getPromotionEvent(promotionEventId);
        if(promotionEvent.getStartDateTime().isBefore(LocalDateTime.now())){
            throw new CustomApiException(ErrorCode.PROMOTION_EVENT_ALREADY);
        }
        if(ValidUtil.isValidNullAndEmpty(promotionEvent.getImageUrl())){
            s3ImageService.deleteImage(promotionEvent.getImageUrl());
        }
        promotionEventRepository.deletePromotionEvent(promotionEventId);
    }

    @Transactional
    public CouponCreateResponseDto couponApplyAndIssuance(Long promotionEventId, User user) {
        PromotionEvent promotionEvent = this.getPromotionEvent(promotionEventId);
        if(promotionEvent.getEndDateTime().isBefore(LocalDateTime.now())){
            throw new CustomApiException(ErrorCode.PROMOTION_EVENT_END);
        }
        if(promotionEvent.getCouponGetCount() == promotionEvent.getTotalCount()){
            throw new CustomApiException(ErrorCode.COUPON_SOLD_OUT);
        }
        Coupon coupon = couponService.createCoupon(promotionEvent, user);
        promotionEvent.couponGetCountUp();
        return new CouponCreateResponseDto(coupon);
    }

    private PromotionEvent getPromotionEvent(Long promotionEventId) {
        return promotionEventRepository.findByIdAndDeletedAtIsNull(promotionEventId)
                .orElseThrow(
                        ()-> new CustomApiException(ErrorCode.PROMOTION_EVENT_NOT)
                );
    }
}
