package com.sparta.popupstore.domain.promotionalevent.service;

import com.sparta.popupstore.domain.popupstore.repository.PopupStoreRepository;
import com.sparta.popupstore.domain.promotionalevent.dto.request.PromotionalEventCreateRequestDto;
import com.sparta.popupstore.domain.promotionalevent.dto.response.PromotionEventResponseDto;
import com.sparta.popupstore.domain.promotionalevent.dto.response.PromotionalEventCreateResponseDto;
import com.sparta.popupstore.domain.promotionalevent.entity.PromotionalEvent;
import com.sparta.popupstore.domain.promotionalevent.repository.PromotionalEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromotionalEventService {

    private final PromotionalEventRepository promotionalEventRepository;
    private final PopupStoreRepository popupStoreRepository;

    public PromotionalEventCreateResponseDto createEvent(
            PromotionalEventCreateRequestDto promotionalEventCreateRequestDto,
            Long popupStoreId
    ) {
        PromotionalEvent promotionalEvent = promotionalEventCreateRequestDto.toEvent();
        if(popupStoreId != null) {
            popupStoreRepository.findById(popupStoreId).ifPresent(promotionalEvent::addPopupStore);
        }
        return new PromotionalEventCreateResponseDto(promotionalEventRepository.save(promotionalEvent));
    }

    public Page<PromotionEventResponseDto> findAllPromotionalEvents(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum-1, pageSize);
        return promotionalEventRepository.findAllPromotionalEvents(pageable).map(PromotionEventResponseDto::new);
    }
}
