package com.sparta.popupstore.domain.promotionevent.service;

import com.sparta.popupstore.domain.popupstore.repository.PopupStoreRepository;
import com.sparta.popupstore.domain.promotionevent.dto.request.PromotionEventCreateRequestDto;
import com.sparta.popupstore.domain.promotionevent.dto.request.PromotionEventUpdateRequestDto;
import com.sparta.popupstore.domain.promotionevent.dto.response.*;
import com.sparta.popupstore.domain.promotionevent.entity.Coupon;
import com.sparta.popupstore.domain.promotionevent.entity.PromotionEvent;
import com.sparta.popupstore.domain.promotionevent.repository.PromotionEventRepository;
import com.sparta.popupstore.domain.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PromotionEventService {

    private final PromotionEventRepository promotionEventRepository;
    private final PopupStoreRepository popupStoreRepository;
    private final CouponService couponService;

    public PromotionEventCreateResponseDto createEvent(
            PromotionEventCreateRequestDto promotionEventCreateRequestDto,
            Long popupStoreId
    ) {
        PromotionEvent promotionEvent = promotionEventCreateRequestDto.toEvent();
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
            PromotionEventUpdateRequestDto promotionEventUpdateRequestDto
            , Long promotionEventId
    ) {
        PromotionEvent promotionEvent = this.getPromotionEvent(promotionEventId);
        if(promotionEvent.getStartDateTime().isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("이미 시작한 이벤트는 수정할 수 없습니다.");
        }
        promotionEvent.updatePromotionEvent(
                promotionEventUpdateRequestDto.getTitle(),
                promotionEventUpdateRequestDto.getDescription(),
                promotionEventUpdateRequestDto.getDiscountPercentage(),
                promotionEventUpdateRequestDto.getTotalCount(),
                promotionEventUpdateRequestDto.getCouponExpirationPeriod(),
                promotionEventUpdateRequestDto.getStartDateTime(),
                promotionEventUpdateRequestDto.getEndDateTime()
        );
        return new PromotionEventUpdateResponseDto(promotionEvent);
    }

    @Transactional
    public void deletePromotionEvent(Long promotionEventId) {
        PromotionEvent promotionEvent = this.getPromotionEvent(promotionEventId);
        if(promotionEvent.getStartDateTime().isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("이미 시작한 이벤트는 삭제할 수 없습니다.");
        }
        promotionEventRepository.deletePromotionEvent(promotionEventId);
    }

    @Transactional
    public CouponCreateResponseDto couponApplyAndIssuance(Long promotionEventId, User user) {
        PromotionEvent promotionEvent = this.getPromotionEvent(promotionEventId);
        if(promotionEvent.getCouponGetCount() == promotionEvent.getTotalCount()){
            throw new IllegalArgumentException("쿠폰이 모두 소진되었습니다.");
        }
        Coupon coupon = couponService.createCoupon(promotionEvent, user);
        promotionEvent.couponGetCountUp();
        return new CouponCreateResponseDto(coupon);
    }

    private PromotionEvent getPromotionEvent(Long promotionEventId) {
        return promotionEventRepository.findByPromotionEventId(promotionEventId)
                .orElseThrow(
                        ()-> new IllegalArgumentException("존재하지 않거나 종료된 이벤트입니다.")
                );
    }
}
