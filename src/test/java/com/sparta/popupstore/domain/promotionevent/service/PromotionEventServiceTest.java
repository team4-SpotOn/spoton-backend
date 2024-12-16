package com.sparta.popupstore.domain.promotionevent.service;

import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.repository.PopupStoreRepository;
import com.sparta.popupstore.domain.promotionevent.dto.request.PromotionEventCreateRequestDto;
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
    void createEventTest1() {
        // given
        Long popupStoreId = 1L;
        PromotionEventCreateRequestDto requestDto = PromotionEventCreateRequestDto.builder().build();
        // when
        String exception = assertThrows(CustomApiException.class , () -> promotionEventService.createEvent(requestDto, popupStoreId)).getMessage();
        // then
        assertEquals("해당 팝업스토어가 없습니다." , exception);
    }

    @Test
    @DisplayName("팝업스토어 고유번호가 null 이라면 이벤트의 팝업스토어 컬럼 null 로 저장")
    void createEventTest2() {
        // given
        Long popupStoreId = null;
        PromotionEventCreateRequestDto requestDto = PromotionEventCreateRequestDto.builder().build();
        PromotionEvent promotionEvent = PromotionEvent.builder()
                .id(3L)
                .title("테스트 제목")
                .totalCount(10)
                .discountPercentage(10)
                .startDateTime(LocalDateTime.now())
                .endDateTime(LocalDateTime.now().plusDays(2))
                .couponExpirationPeriod(30)
                .couponGetCount(0)
                .build();
        // when
        when(promotionEventRepository.save(any())).thenReturn(promotionEvent);
        PromotionEventCreateResponseDto responseDto = promotionEventService.createEvent(requestDto, popupStoreId);

        // then
        assertNull(responseDto.getPopupStoreId());
    }

    @Test
    @DisplayName("팝업스토어 고유번호가 null 이 아니라면 팝업스토어 고유번호 저장")
    void createEventTest3() {
        // given
        Long popupStoreId = 1L;
        PromotionEventCreateRequestDto requestDto = PromotionEventCreateRequestDto.builder()
                .title("테스트 제목")
                .totalCount(10)
                .discountPercentage(10)
                .startDateTime(LocalDateTime.now())
                .endDateTime(LocalDateTime.now().plusDays(2))
                .couponExpirationPeriod(30)
                .build();
        PopupStore popupStore = PopupStore.builder()
                .id(popupStoreId)
                .endDate(LocalDate.now().plusDays(3))
                .build();
        PromotionEvent promotionEvent = PromotionEvent.builder()
                .id(3L)
                .title("테스트 제목")
                .popupStore(popupStore)
                .totalCount(10)
                .discountPercentage(10)
                .startDateTime(LocalDateTime.now())
                .endDateTime(LocalDateTime.now().plusDays(2))
                .couponExpirationPeriod(30)
                .couponGetCount(0)
                .build();
        // when
        when(popupStoreRepository.findByIdAndEndDateAfter(popupStoreId, LocalDate.now())).thenReturn(Optional.ofNullable(popupStore));
        when(promotionEventRepository.save(any())).thenReturn(promotionEvent);
        PromotionEventCreateResponseDto responseDto = promotionEventService.createEvent(requestDto, popupStoreId);

        // then
        assertEquals(popupStoreId, responseDto.getPopupStoreId());
    }

    @Test
    @DisplayName("이벤트 종료일을 팝업스토어 종료일 이후로 선택했을 시 예외처리")
    void createEventTest4() {
        // given
        Long popupStoreId = 1L;
        PromotionEventCreateRequestDto requestDto = PromotionEventCreateRequestDto.builder()
                .title("테스트 제목")
                .totalCount(10)
                .discountPercentage(10)
                .startDateTime(LocalDateTime.now())
                .endDateTime(LocalDateTime.now().plusDays(2))
                .couponExpirationPeriod(30)
                .build();
        PopupStore popupStore = PopupStore.builder()
                .id(popupStoreId)
                .startDate(LocalDate.now().minusDays(12))
                .endDate(LocalDate.now())
                .build();
        // when
        when(popupStoreRepository.findByIdAndEndDateAfter(popupStoreId, LocalDate.now())).thenReturn(Optional.ofNullable(popupStore));
        Throwable exception = assertThrows(CustomApiException.class, ()->
                promotionEventService.createEvent(requestDto, popupStoreId));
        // then
        assertEquals("이벤트 종료일은 팝업스토어 종료일 이후로 선택할 수 없습니다.", exception.getMessage());
    }
}