package com.sparta.popupstore.domain.promotionevent.service;

import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.repository.PopupStoreRepository;
import com.sparta.popupstore.domain.promotionevent.dto.request.PromotionEventCreateRequestDto;
import com.sparta.popupstore.domain.promotionevent.dto.request.PromotionEventUpdateRequestDto;
import com.sparta.popupstore.domain.promotionevent.dto.response.PromotionEventCreateResponseDto;
import com.sparta.popupstore.domain.promotionevent.dto.response.PromotionEventUpdateResponseDto;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PromotionEventServiceTest {
    @Mock
    private PromotionEventRepository promotionEventRepository;
    @Mock
    private PopupStoreRepository popupStoreRepository;
    @Mock
    private PromotionEvent mockPromotionEvent;
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
    @DisplayName("존재하지 않거나 시작일이 지났거나 종료일이 지나지 않은 이벤트 수정 요청 시 예외 처리")
    void updatePromotionEventTest1() {
        // given
        Long promotionEventId = 1L;
        PromotionEventUpdateRequestDto requestDto = PromotionEventUpdateRequestDto.builder()
                .build();
        // when
        when(promotionEventRepository.findByIdAndStartDateTimeAfterOrEndDateTimeBefore(any(), any(), any())).thenReturn(Optional.empty());
        Throwable exception = assertThrows(CustomApiException.class, ()->
                promotionEventService.updatePromotionEvent(requestDto, promotionEventId));
        // then
        assertEquals("수정하거나 삭제할 수 없는 이벤트입니다.", exception.getMessage());
    }

    @Test
    @DisplayName("이벤트 수정 성공")
    void updatePromotionEventTest2() {
        // given
        Long promotionEventId = 1L;
        PromotionEvent promotionEvent = PromotionEvent.builder()
                .id(promotionEventId)
                .title("수정 전 제목")
                .build();
        PromotionEventUpdateRequestDto requestDto = PromotionEventUpdateRequestDto.builder()
                .title("수정 후 제목")
                .build();
        // when
        when(promotionEventRepository.findByIdAndStartDateTimeAfterOrEndDateTimeBefore(any(), any(), any())).thenReturn(Optional.of(promotionEvent));
        PromotionEventUpdateResponseDto responseDto = promotionEventService.updatePromotionEvent(requestDto, promotionEventId);
        // then
        assertEquals("수정 후 제목", responseDto.getTitle());
    }

    @Test
    @DisplayName("존재하지 않거나 시작일이 지났거나 종료일이 지나지 않은 이벤트 삭제 요청 시 예외 처리")
    void deletePromotionEventTest1() {
        // given
        Long promotionEventId = 1L;
        // when
        when(promotionEventRepository.findByIdAndStartDateTimeAfterOrEndDateTimeBefore(any(), any(), any())).thenReturn(Optional.empty());
        Throwable exception = assertThrows(CustomApiException.class, ()->
                promotionEventService.deletePromotionEvent(promotionEventId)
        );
        // then
        assertEquals("수정하거나 삭제할 수 없는 이벤트입니다.", exception.getMessage());
    }

    @Test
    @DisplayName("삭제 성공")
    void deletePromotionEventTest2() {
        // given
        Long promotionEventId = 1L;
        // when
        when(promotionEventRepository.findByIdAndStartDateTimeAfterOrEndDateTimeBefore(any(), any(), any())).thenReturn(Optional.of(mockPromotionEvent));
        promotionEventService.deletePromotionEvent(promotionEventId);
        // then
        verify(mockPromotionEvent, times(1)).delete(any());
    }
}
