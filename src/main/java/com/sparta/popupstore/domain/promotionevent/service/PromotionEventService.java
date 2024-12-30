package com.sparta.popupstore.domain.promotionevent.service;

import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
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
        PromotionEvent promotionEvent = promotionEventRepository.findById(promotionEventId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.PROMOTION_EVENT_NOT_FOUND));
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
        PromotionEvent promotionEvent = this.getPromotionEventByAfterStartDate(promotionEventId);
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
        PromotionEvent promotionEvent = this.getPromotionEventByAfterStartDate(promotionEventId);
        promotionEvent.delete(LocalDateTime.now());
    }

    private PromotionEvent getPromotionEventByAfterStartDate(Long promotionEventId) {
        return promotionEventRepository.findByIdAndStartDateTimeAfter(promotionEventId, LocalDateTime.now())
                .orElseThrow(() -> new CustomApiException(ErrorCode.PROMOTION_EVENT_NOT_UPDATE_AND_DELETE));
    }
}
