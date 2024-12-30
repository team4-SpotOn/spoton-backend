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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
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
        assertEquals(ErrorCode.POPUP_STORE_NOT_FOUND.getMessage() , exception);
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
        assertEquals(ErrorCode.PROMOTION_EVENT_NOT_AFTER_POPUP_STORE_END_DATE.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("존재하지 않는 이벤트 수정 요청 시 예외 처리")
    void updatePromotionEventTest1() {
        // given
        Long promotionEventId = 1L;
        PromotionEventUpdateRequestDto requestDto = PromotionEventUpdateRequestDto.builder()
                .build();
        // when
        when(promotionEventRepository.findById(promotionEventId)).thenReturn(Optional.empty());
        Throwable exception = assertThrows(CustomApiException.class, ()->
                promotionEventService.updatePromotionEvent(requestDto, promotionEventId));
        // then
        assertEquals(ErrorCode.PROMOTION_EVENT_NOT_FOUND.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("이미 진행중인 이벤트 수정 요청 시 예외 처리")
    void updatePromotionEventTest2() {
        // given
        Long promotionEventId = 1L;
        LocalDateTime now = LocalDateTime.now();
        PromotionEvent promotionEvent = PromotionEvent.builder()
                .id(promotionEventId)
                .startDateTime(now.minusDays(2))
                .endDateTime(now.plusDays(2))
                .build();
        PromotionEventUpdateRequestDto requestDto = PromotionEventUpdateRequestDto.builder()
                .build();
        // when
        when(promotionEventRepository.findById(promotionEventId)).thenReturn(Optional.of(promotionEvent));
        Throwable exception = assertThrows(CustomApiException.class, ()->
                promotionEventService.updatePromotionEvent(requestDto, promotionEventId));
        // then
        assertEquals(ErrorCode.PROMOTION_EVENT_IN_PROGRESS.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("이벤트 수정 성공")
    void updatePromotionEventTest3() {
        // given
        Long promotionEventId = 1L;
        LocalDateTime now = LocalDateTime.now();
        PromotionEvent promotionEvent = PromotionEvent.builder()
                .id(promotionEventId)
                .startDateTime(now.plusDays(2))
                .endDateTime(now.plusDays(2))
                .title("수정 전 제목")
                .build();
        PromotionEventUpdateRequestDto requestDto = PromotionEventUpdateRequestDto.builder()
                .title("수정 후 제목")
                .build();
        // when
        when(promotionEventRepository.findById(promotionEventId)).thenReturn(Optional.of(promotionEvent));
        PromotionEventUpdateResponseDto responseDto = promotionEventService.updatePromotionEvent(requestDto, promotionEventId);
        // then
        assertEquals(requestDto.getTitle(), responseDto.getTitle());
    }

    @Test
    @DisplayName("존재하지 않는 이벤트 삭제 요청 시 예외 처리")
    void deletePromotionEventTest1() {
        // given
        Long promotionEventId = 1L;
        // when
        when(promotionEventRepository.findById(promotionEventId)).thenReturn(Optional.empty());
        Throwable exception = assertThrows(CustomApiException.class, ()->
                promotionEventService.deletePromotionEvent(promotionEventId)
        );
        // then
        assertEquals(ErrorCode.PROMOTION_EVENT_NOT_FOUND.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("이미 진행중인 이벤트 삭제 요청 시 예외 처리")
    void deletePromotionEventTest2() {
        // given
        Long promotionEventId = 1L;
        LocalDateTime now = LocalDateTime.now();
        PromotionEvent promotionEvent = PromotionEvent.builder()
                .id(promotionEventId)
                .startDateTime(now.minusDays(2))
                .endDateTime(now.plusDays(2))
                .build();
        // when
        when(promotionEventRepository.findById(promotionEventId)).thenReturn(Optional.of(promotionEvent));
        Throwable exception = assertThrows(CustomApiException.class, ()->
                promotionEventService.deletePromotionEvent(promotionEventId));
        // then
        assertEquals(ErrorCode.PROMOTION_EVENT_IN_PROGRESS.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("이벤트 삭제 성공")
    void deletePromotionEventTest3() {
        // given
        Long promotionEventId = 1L;
        LocalDateTime now = LocalDateTime.now();
        PromotionEvent promotionEvent = spy(PromotionEvent.builder()
                .id(promotionEventId)
                .startDateTime(now.plusDays(2))
                .endDateTime(now.plusDays(2))
                .build());
        // when
        when(promotionEventRepository.findById(promotionEventId)).thenReturn(Optional.of(promotionEvent));
        promotionEventService.deletePromotionEvent(promotionEventId);
        // then
        verify(promotionEvent, times(1)).delete(any(LocalDateTime.class));
    }

    @Test
    @DisplayName("이벤트 단건조회 시 존재하지 않는 이벤트 일 시 예외처리")
    void findOnePromotionEventTest1() {
        // given
        Long promotionEventId = 1L;
        // when
        when(promotionEventRepository.findById(promotionEventId)).thenReturn(Optional.empty());
        Throwable exception = assertThrows(CustomApiException.class, ()->
                promotionEventService.findOnePromotionEvent(promotionEventId)
        );
        // then
        assertEquals(ErrorCode.PROMOTION_EVENT_NOT_FOUND.getMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("이벤트 단건조회 성공 - 팝업스토어에서 진행하는 이벤트가 아닐 경우")
    void findOnePromotionEventTest2() {
        // given
        Long promotionEventId = 1L;
        PromotionEvent promotionEvent = PromotionEvent.builder()
                .id(promotionEventId)
                .title("제목")
                .build();
        // when
        when(promotionEventRepository.findById(promotionEventId)).thenReturn(Optional.of(promotionEvent));
        PromotionEventFindOneResponseDto responseDto = promotionEventService.findOnePromotionEvent(promotionEventId);
        // then
        assertEquals(promotionEvent.getTitle(), responseDto.getTitle());
    }

    @Test
    @DisplayName("이벤트 단건조회 성공 - 팝업스토어에서 진행하는 이벤트일 경우")
    void findOnePromotionEventTest3() {
        // given
        Long promotionEventId = 1L;
        Long popupStoreId = 1L;
        PromotionEvent promotionEvent = PromotionEvent.builder()
                .id(promotionEventId)
                .popupStoreId(popupStoreId)
                .title("제목")
                .build();
        PopupStore popupStore = PopupStore.builder()
                .id(popupStoreId)
                .build();
        // when
        when(promotionEventRepository.findById(promotionEventId)).thenReturn(Optional.of(promotionEvent));
        when(popupStoreRepository.findById(popupStoreId)).thenReturn(Optional.of(popupStore));
        PromotionEventFindOneResponseDto responseDto = promotionEventService.findOnePromotionEvent(promotionEventId);
        // then
        assertEquals(promotionEvent.getTitle(), responseDto.getTitle());
        assertEquals(popupStoreId, responseDto.getPromotionEventFindPopupStoreResponseDto().getId());
    }

    @Test
    @DisplayName("이벤트 다건조회 성공")
    void findAllPromotionEventTest1() {
        // given
        PromotionEvent promotionEvent1 = PromotionEvent.builder()
                .id(1L)
                .build();
        PromotionEvent promotionEvent2 = PromotionEvent.builder()
                .id(2L)
                .build();
        PromotionEvent promotionEvent3 = PromotionEvent.builder()
                .id(3L)
                .build();
        Pageable pageable = PageRequest.of(0, 10);
        List<PromotionEvent> promotionEvents = Arrays.asList(promotionEvent1, promotionEvent2, promotionEvent3);
        Page<PromotionEvent> promotionEventsPage = new PageImpl<>(promotionEvents, pageable, promotionEvents.size());

        // when
        when(promotionEventRepository.findAll(pageable)).thenReturn(promotionEventsPage);
        Page<PromotionEventFindAllResponseDto> responseDto = promotionEventService.findAllPromotionalEvents(1, 10);
        // then
        assertEquals(promotionEvents.size(), responseDto.getTotalElements());
    }
}
