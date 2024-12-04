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

@Service
@RequiredArgsConstructor
public class PromotionEventService {

    private final PromotionEventRepository promotionEventRepository;
    private final PopupStoreRepository popupStoreRepository;

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

    public Page<PromotionEventFindResponseDto> findAllPromotionalEvents(int page, int size) {
        Pageable pageable = PageRequest.of(page-1, size);
        return promotionEventRepository.findAllPromotionalEvents(pageable).map(PromotionEventFindResponseDto::new);
    }

    @Transactional
    public PromotionEventUpdateResponseDto updatePromotionEvent(PromotionEventUpdateRequestDto promotionEventUpdateRequestDto, Long promotionEventId) {
        PromotionEvent promotionEvent = promotionEventRepository.findByPromotionEventId(promotionEventId);
        promotionEvent.updatePromotionEvent(
                promotionEventUpdateRequestDto.getTitle(),
                promotionEventUpdateRequestDto.getDescription(),
                promotionEventUpdateRequestDto.getDiscountPercentage(),
                promotionEventUpdateRequestDto.getTotalCount(),
                promotionEventUpdateRequestDto.getStartDateTime(),
                promotionEventUpdateRequestDto.getEndDateTime()
        );
        return new PromotionEventUpdateResponseDto(promotionEvent);
    }

    @Transactional
    public void deletePromotionEvent(Long promotionEventId) {
        promotionEventRepository.findByPromotionEventId(promotionEventId);
        promotionEventRepository.deletePromotionEvent(promotionEventId);
    }
}
