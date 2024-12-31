package com.sparta.popupstore.domain.reservation.service;

import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.domain.point.service.PointService;
import com.sparta.popupstore.domain.popupstore.bundle.service.PopupStoreBundleService;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.repository.PopupStoreRepository;
import com.sparta.popupstore.domain.reservation.dto.request.ReservationCreateRequestDto;
import com.sparta.popupstore.domain.reservation.dto.response.ReservationCreateResponseDto;
import com.sparta.popupstore.domain.reservation.entity.Reservation;
import com.sparta.popupstore.domain.reservation.repository.ReservationRepository;
import com.sparta.popupstore.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    PopupStoreRepository popupStoreRepository;
    @Mock
    ReservationRepository reservationRepository;
    @Mock
    PointService pointService;
    @Mock
    PopupStoreBundleService popupStoreBundleService;

    @InjectMocks
    ReservationService reservationService;

    @Nested
    @Transactional
    @DisplayName("10명 예약 가능한 팝업스토어 예약 인원수 별 테스트")
    class NumberTest {
        @Test
        @Transactional(propagation = Propagation.NESTED)
        @DisplayName("5명 예약")
        void test1() {
            // given
            LocalDate reservationDate = LocalDate.of(2020, 1, 1);
            LocalTime reservationTime = LocalTime.of(8, 0);
            Integer number = 5;
            ReservationCreateRequestDto requestDto = new ReservationCreateRequestDto(
                    reservationDate,
                    reservationTime,
                    number,
                    null
            );

            User user = getTestUser();
            Long popupStoreId = 1L;
            PopupStore popupStore = getTestPopupStore(popupStoreId);

            when(popupStoreRepository.findById(popupStoreId)).thenReturn(Optional.of(popupStore));
            doNothing().when(popupStoreBundleService).reservationValid(any(), any(), any());
            when(reservationRepository.findAllByPopupStoreAndReservationDate(any(PopupStore.class), any(LocalDate.class))).thenReturn(List.of());
            doNothing().when(pointService).pointUsed(any(), any(), any(), any());
            when(reservationRepository.save(any(Reservation.class))).thenReturn(requestDto.toEntity(user, popupStore));

            // when
            ReservationCreateResponseDto responseDto = reservationService.createReservation(user, popupStoreId, requestDto);

            // then
            assertNotNull(responseDto);
            assertEquals(reservationDate, responseDto.getReservationDate());
            assertEquals(reservationTime, responseDto.getReservationTime());
            assertEquals(number, responseDto.getNumber());
        }

        @Test
        @Transactional(propagation = Propagation.NESTED)
        @DisplayName("20명 예약")
        void test2() {
            // given
            LocalDate reservationDate = LocalDate.of(2020, 1, 1);
            LocalTime reservationTime = LocalTime.of(8, 0);
            Integer number = 20;
            ReservationCreateRequestDto requestDto = new ReservationCreateRequestDto(
                    reservationDate,
                    reservationTime,
                    number,
                    null
            );

            User user = getTestUser();
            Long popupStoreId = 1L;
            PopupStore popupStore = getTestPopupStore(popupStoreId);

            when(popupStoreRepository.findById(popupStoreId)).thenReturn(Optional.of(popupStore));
            doNothing().when(popupStoreBundleService).reservationValid(any(), any(), any());
            when(reservationRepository.findAllByPopupStoreAndReservationDate(any(PopupStore.class), any(LocalDate.class))).thenReturn(List.of());

            // when & then
            CustomApiException exception = assertThrows(CustomApiException.class,
                    () -> reservationService.createReservation(user, popupStoreId, requestDto));
            assertEquals(ErrorCode.RESERVATION_LIMIT_OVER, exception.getErrorCode());
        }

        @Test
        @Transactional(propagation = Propagation.NESTED)
        @DisplayName("5명 예약된 상태에서 8명 예약")
        void test3() {
            // given
            LocalDate reservationDate = LocalDate.of(2020, 1, 1);
            LocalTime reservationTime = LocalTime.of(8, 0);
            Integer number1 = 5;
            Integer number2 = 8;

            ReservationCreateRequestDto requestDto = new ReservationCreateRequestDto(
                    reservationDate,
                    reservationTime,
                    number2,
                    null
            );

            User user = getTestUser();
            Long popupStoreId = 1L;
            PopupStore popupStore = getTestPopupStore(popupStoreId);
            Reservation preReservation = getTestReservation(
                    1L,
                    user,
                    popupStore,
                    reservationDate,
                    reservationTime,
                    number1
            );

            when(popupStoreRepository.findById(popupStoreId)).thenReturn(Optional.of(popupStore));
            doNothing().when(popupStoreBundleService).reservationValid(any(), any(), any());
            when(reservationRepository.findAllByPopupStoreAndReservationDate(any(PopupStore.class), any(LocalDate.class))).thenReturn(List.of(preReservation));

            // when & then
            CustomApiException exception = assertThrows(CustomApiException.class,
                    () -> reservationService.createReservation(user, popupStoreId, requestDto));
            assertEquals(ErrorCode.RESERVATION_LIMIT_OVER, exception.getErrorCode());
        }
    }

    private User getTestUser(Long userId, Integer point) {
        return User.builder()
                .id(userId)
                .point(point)
                .build();
    }
    private User getTestUser(Long userId) {
        return getTestUser(userId, 10000);
    }
    private User getTestUser(Integer point) {
        return getTestUser(1L, point);
    }
    private User getTestUser() {
        return getTestUser(1L, 10000);
    }

    private PopupStore getTestPopupStore(Long popupStoreId, Integer price, Integer reservationLimit, LocalDate startDate, LocalDate endDate) {
        return PopupStore.builder()
                .id(popupStoreId)
                .price(price)
                .reservationLimit(reservationLimit)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
    private PopupStore getTestPopupStore(Long popupStoreId, Integer price, Integer reservationLimit) {
        LocalDate now = LocalDate.now();
        return getTestPopupStore(popupStoreId, price, reservationLimit, now.plusDays(1), now.plusMonths(1));
    }
    private PopupStore getTestPopupStore(Long popupStoreId, Integer reservationLimit) {
        return getTestPopupStore(popupStoreId, 100, reservationLimit);
    }
    private PopupStore getTestPopupStore(Long popupStoreId) {
        return getTestPopupStore(popupStoreId, 10);
    }
    private PopupStore getTestPopupStore() {
        return getTestPopupStore(1L);
    }

    private Reservation getTestReservation(Long reservationId, User user, PopupStore popupStore, LocalDate reservationDate, LocalTime reservationTime, Integer number) {
        return Reservation.builder()
                .id(reservationId)
                .user(user)
                .popupStore(popupStore)
                .reservationDate(reservationDate)
                .reservationTime(reservationTime)
                .number(number)
                .build();
    }
}
