package com.sparta.popupstore.domain.promotionevent.service;

import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.repository.PopupStoreRepository;
import com.sparta.popupstore.domain.promotionevent.dto.request.PromotionEventCreateRequestDto;
import com.sparta.popupstore.domain.promotionevent.dto.request.PromotionEventUpdateRequestDto;
import com.sparta.popupstore.domain.promotionevent.dto.response.PromotionEventCreateResponseDto;
import com.sparta.popupstore.domain.promotionevent.entity.PromotionEvent;
import com.sparta.popupstore.domain.promotionevent.repository.PromotionEventRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PromotionEventServiceTest {
    @Mock
    private PromotionEventRepository promotionEventRepository;
    @Mock
    private PopupStoreRepository popupStoreRepository;
    @InjectMocks
    private PromotionEventService promotionEventService;

    @Test
    @DisplayName("팝업스토어 고유번호가 null 이 아닐 시에 팝업스토어가 존재하지 않거나 종료되었을 때 예외처리")
    void createPromotionEventTest1() {
        // given
        Long popupStoreId = 1L;
        PromotionEventCreateRequestDto requestDto = PromotionEventCreateRequestDto.builder()
                .popupStoreId(popupStoreId)
                .build();
        // when
        String exception = assertThrows(CustomApiException.class , () -> promotionEventService.createPromotionEvent(requestDto)).getMessage();
        // then
        assertEquals("해당 팝업스토어가 없습니다." , exception);
    }

    @Test
    @DisplayName("팝업스토어 고유번호가 null 이라면 이벤트의 팝업스토어 컬럼 null 로 저장")
    void createPromotionEventTest2() {
        // given
        PromotionEventCreateRequestDto requestDto = PromotionEventCreateRequestDto.builder().popupStoreId(null).build();
        PromotionEvent promotionEvent = PromotionEvent.builder()
                .build();
        // when
        when(promotionEventRepository.save(any())).thenReturn(promotionEvent);
        PromotionEventCreateResponseDto responseDto = promotionEventService.createPromotionEvent(requestDto);

        // then
        assertNull(responseDto.getPopupStoreId());
    }

    @Test
    @DisplayName("팝업스토어 고유번호가 null 이 아니라면 팝업스토어 고유번호 저장")
    void createPromotionEventTest3() {
        // given
        Long popupStoreId = 1L;
        PromotionEventCreateRequestDto requestDto = PromotionEventCreateRequestDto.builder()
                .popupStoreId(popupStoreId)
                .endDateTime(LocalDateTime.now().plusDays(2))
                .build();
        PopupStore popupStore = PopupStore.builder()
                .id(popupStoreId)
                .endDate(LocalDate.now().plusDays(3))
                .build();
        PromotionEvent promotionEvent = PromotionEvent.builder()
                .id(3L)
                .popupStoreId(popupStoreId)
                .endDateTime(LocalDateTime.now().plusDays(2))
                .build();
        // when
        when(popupStoreRepository.findByIdAndEndDateAfter(popupStoreId, LocalDate.now())).thenReturn(Optional.ofNullable(popupStore));
        when(promotionEventRepository.save(any())).thenReturn(promotionEvent);
        PromotionEventCreateResponseDto responseDto = promotionEventService.createPromotionEvent(requestDto);

        // then
        assertEquals(popupStoreId, responseDto.getPopupStoreId());
    }

    @Test
    @DisplayName("이벤트 종료일을 팝업스토어 종료일 이후로 선택했을 시 예외처리")
    void createPromotionEventTest4() {
        // given
        Long popupStoreId = 1L;
        PromotionEventCreateRequestDto requestDto = PromotionEventCreateRequestDto.builder()
                .popupStoreId(popupStoreId)
                .endDateTime(LocalDateTime.now().plusDays(2))
                .build();
        PopupStore popupStore = PopupStore.builder()
                .id(popupStoreId)
                .endDate(LocalDate.now())
                .build();
        // when
        when(popupStoreRepository.findByIdAndEndDateAfter(popupStoreId, LocalDate.now())).thenReturn(Optional.ofNullable(popupStore));
        Throwable exception = assertThrows(CustomApiException.class, ()->
                promotionEventService.createPromotionEvent(requestDto));
        // then
        assertEquals("이벤트 종료일은 팝업스토어 종료일 이후로 선택할 수 없습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("존재하지 않는 이벤트 수정 요청 시 예외 처리")
    void updatePromotionEventTest1() {
        // given
        Long promotionEventId = 1L;
        PromotionEventUpdateRequestDto requestDto = PromotionEventUpdateRequestDto.builder()
                .endDateTime(LocalDateTime.now().plusDays(2))
                .build();
        // when
        when(promotionEventRepository.findById(any())).thenReturn(Optional.empty());
        Throwable exception = assertThrows(CustomApiException.class, ()->
                promotionEventService.updatePromotionEvent(requestDto, promotionEventId));
        // then
        assertEquals("해당 이벤트가 없습니다.", exception.getMessage());
    }
}
