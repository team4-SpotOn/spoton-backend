package com.sparta.popupstore.domain.promotionevent.service;

import com.sparta.popupstore.domain.popupstore.repository.PopupStoreRepository;
import com.sparta.popupstore.domain.promotionevent.dto.request.PromotionEventCreateRequestDto;
import com.sparta.popupstore.domain.promotionevent.dto.request.PromotionEventUpdateRequestDto;
import com.sparta.popupstore.domain.promotionevent.dto.response.PromotionEventFindResponseDto;
import com.sparta.popupstore.domain.promotionevent.dto.response.PromotionEventCreateResponseDto;
import com.sparta.popupstore.domain.promotionevent.dto.response.PromotionEventUpdateResponseDto;
import com.sparta.popupstore.domain.promotionevent.entity.PromotionEvent;
import com.sparta.popupstore.domain.promotionevent.repository.PromotionEventRepository;
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

    public PromotionEventCreateResponseDto createEvent(
            PromotionEventCreateRequestDto promotionEventCreateRequestDto,
            Long popupStoreId
    ) {
        this.validDateTime(promotionEventCreateRequestDto.getStartDateTime(), promotionEventCreateRequestDto.getEndDateTime());
        PromotionEvent promotionEvent = promotionEventCreateRequestDto.toEvent();
        this.addOrUpdatePopupStore(popupStoreId, promotionEvent);
        return new PromotionEventCreateResponseDto(promotionEventRepository.save(promotionEvent));
    }

    public Page<PromotionEventFindResponseDto> findAllPromotionalEvents(int page, int size) {
        Pageable pageable = PageRequest.of(page-1, size);
        return promotionEventRepository.findAllPromotionalEvents(pageable).map(PromotionEventFindResponseDto::new);
    }

    @Transactional
    public PromotionEventUpdateResponseDto updatePromotionEvent(PromotionEventUpdateRequestDto promotionEventUpdateRequestDto, Long promotionEventId, Long popupStoreId) {
        this.validDateTime(promotionEventUpdateRequestDto.getStartDateTime(), promotionEventUpdateRequestDto.getEndDateTime());
        PromotionEvent promotionEvent = promotionEventRepository.findById(promotionEventId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이벤트입니다."));
        promotionEvent.updatePromotionEvent(
                promotionEventUpdateRequestDto.getTitle(),
                promotionEventUpdateRequestDto.getDescription(),
                promotionEventUpdateRequestDto.getDiscountPercentage(),
                promotionEventUpdateRequestDto.getTotalCount(),
                promotionEventUpdateRequestDto.getStartDateTime(),
                promotionEventUpdateRequestDto.getEndDateTime()
        );
        this.addOrUpdatePopupStore(popupStoreId, promotionEvent);
        return new PromotionEventUpdateResponseDto(promotionEvent);
    }

    private void validDateTime(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        if(startDateTime.isAfter(endDateTime)) {
            throw new IllegalArgumentException("시작일은 종료일보다 늦을 수 없습니다.");
        }
    }

    private void addOrUpdatePopupStore(Long popupStoreId, PromotionEvent promotionEvent) {
        if(popupStoreId != null) {
            popupStoreRepository.findById(popupStoreId).ifPresent(promotionEvent::addPopupStore);
        }
    }
}
