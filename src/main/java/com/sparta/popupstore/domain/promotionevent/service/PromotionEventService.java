package com.sparta.popupstore.domain.promotionevent.service;

import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.domain.coupon.dto.response.CouponCreateResponseDto;
import com.sparta.popupstore.domain.coupon.entity.Coupon;
import com.sparta.popupstore.domain.coupon.service.CouponService;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.repository.PopupStoreRepository;
import com.sparta.popupstore.domain.promotionevent.dto.request.PromotionEventCreateRequestDto;
import com.sparta.popupstore.domain.promotionevent.dto.request.PromotionEventUpdateRequestDto;
import com.sparta.popupstore.domain.promotionevent.dto.response.PromotionEventCreateResponseDto;
import com.sparta.popupstore.domain.promotionevent.dto.response.PromotionEventFindAllResponseDto;
import com.sparta.popupstore.domain.promotionevent.dto.response.PromotionEventFindOneResponseDto;
import com.sparta.popupstore.domain.promotionevent.dto.response.PromotionEventUpdateResponseDto;
import com.sparta.popupstore.domain.promotionevent.entity.PromotionEvent;
import com.sparta.popupstore.domain.promotionevent.repository.PromotionEventRepository;
import com.sparta.popupstore.domain.user.entity.User;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class PromotionEventService {

    private final PromotionEventRepository promotionEventRepository;
    private final PopupStoreRepository popupStoreRepository;
    private final CouponService couponService;

    public PromotionEventCreateResponseDto createPromotionEvent(
            PromotionEventCreateRequestDto requestDto
    ) {
        if(requestDto.getPopupStoreId() != null) {
            PopupStore popupStore = popupStoreRepository.findByIdAndEndDateAfter(requestDto.getPopupStoreId(), LocalDate.now())
                    .orElseThrow(() -> new CustomApiException(ErrorCode.POPUP_STORE_NOT_FOUND));
            if(requestDto.getEndDateTime().toLocalDate().isAfter(popupStore.getEndDate())) {
                throw new CustomApiException(ErrorCode.PROMOTION_EVENT_NOT_AFTER_POPUP_STORE_END_DATE);
            }
        }

        PromotionEvent promotionEvent = requestDto.toEntity();
        return new PromotionEventCreateResponseDto(promotionEventRepository.save(promotionEvent));
    }

    public Page<PromotionEventFindAllResponseDto> findAllPromotionalEvents(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return promotionEventRepository.findAll(pageable)
                .map(event -> {
                    if(event.getPopupStoreId() == null) {
                        return new PromotionEventFindAllResponseDto(event, null);
                    }
                    PopupStore popupStore = popupStoreRepository.findById(event.getPopupStoreId()).orElse(null);
                    return new PromotionEventFindAllResponseDto(event, popupStore);
                });
    }

    @Transactional(readOnly = true)
    public PromotionEventFindOneResponseDto findOnePromotionEvent(Long promotionEventId) {
        PromotionEvent promotionEvent = this.getPromotionEvent(promotionEventId);
        if(promotionEvent.getPopupStoreId() == null) {
            return new PromotionEventFindOneResponseDto(promotionEvent, null);
        }
        PopupStore popupStore = popupStoreRepository.findById(promotionEvent.getPopupStoreId()).orElse(null);
        return new PromotionEventFindOneResponseDto(promotionEvent, popupStore);
    }

    @Transactional
    public PromotionEventUpdateResponseDto updatePromotionEvent(
            PromotionEventUpdateRequestDto requestDto,
            Long promotionEventId
    ) {
        PromotionEvent promotionEvent = this.getPromotionEvent(promotionEventId);
        this.validDate(promotionEvent);
        promotionEvent.updatePromotionEvent(
                requestDto.getTitle(),
                requestDto.getDescription(),
                requestDto.getDiscountPercentage(),
                requestDto.getTotalCount(),
                requestDto.getCouponExpirationPeriod(),
                requestDto.getStartDateTime(),
                requestDto.getEndDateTime(),
                requestDto.getImageUrl()
        );
        return new PromotionEventUpdateResponseDto(promotionEvent);
    }

    @Transactional
    public void deletePromotionEvent(Long promotionEventId) {
        PromotionEvent promotionEvent = this.getPromotionEvent(promotionEventId);
        this.validDate(promotionEvent);
        promotionEvent.delete(LocalDateTime.now());
    }

    private PromotionEvent getPromotionEvent(Long promotionEventId) {
        return promotionEventRepository.findById(promotionEventId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.PROMOTION_EVENT_NOT_FOUND));
    }

    private void validDate(PromotionEvent promotionEvent) {
        LocalDateTime now = LocalDateTime.now();
        if(promotionEvent.getStartDateTime().isBefore(now) && promotionEvent.getEndDateTime().isAfter(now)) {
            throw new CustomApiException(ErrorCode.PROMOTION_EVENT_IN_PROGRESS);
        }
    }

    @Transactional
    public CouponCreateResponseDto couponApplyAndIssuance(User user, Long promotionEventId) {
        System.out.println("쿠폰 발급 시작");
        PromotionEvent promotionEvent = promotionEventRepository.findByIdWithPessimisticLock(promotionEventId);
        if(promotionEvent.getEndDateTime().isBefore(LocalDateTime.now())) {
            throw new CustomApiException(ErrorCode.PROMOTION_EVENT_END);
        }
        if(Objects.equals(promotionEvent.getCouponGetCount(), promotionEvent.getTotalCount())) {
            throw new CustomApiException(ErrorCode.COUPON_SOLD_OUT);
        }
        Coupon coupon = couponService.createCoupon(promotionEvent, user);
        System.out.println();
        log.info("선착순 개수 확인 :{}", promotionEvent.getCouponGetCount());
        // 선착순 개수 +
        promotionEvent.couponGetCountUp();
        System.out.println();
        log.info("선착순 개수 확인 :{}", promotionEvent.getCouponGetCount());

//        // couponGetCount 업데이트
//        promotionEventRepository.couponGetCountUp(promotionEventId);

        return new CouponCreateResponseDto(coupon);
    }
}
