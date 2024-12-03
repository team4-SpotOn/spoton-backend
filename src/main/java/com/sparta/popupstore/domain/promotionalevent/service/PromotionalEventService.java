package com.sparta.popupstore.domain.promotionalevent.service;

import com.sparta.popupstore.domain.popupstore.repository.PopupStoreRepository;
import com.sparta.popupstore.domain.promotionalevent.dto.request.PromotionalEventSaveRequestDto;
import com.sparta.popupstore.domain.promotionalevent.entity.PromotionalEvent;
import com.sparta.popupstore.domain.promotionalevent.repository.PromotionalEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromotionalEventService {

    private final PromotionalEventRepository promotionalEventRepository;
    private final PopupStoreRepository popupStoreRepository;

    public void createEvent(
            PromotionalEventSaveRequestDto promotionalEventSaveRequestDto,
            Long popupStoreId
    ) {
        PromotionalEvent promotionalEvent = promotionalEventSaveRequestDto.toEvent();
        promotionalEvent.addPopupStoreId(popupStoreId);
        promotionalEventRepository.save(promotionalEvent);
    }
}
