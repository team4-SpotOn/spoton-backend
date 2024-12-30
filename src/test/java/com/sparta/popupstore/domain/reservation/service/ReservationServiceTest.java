package com.sparta.popupstore.domain.reservation.service;

import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.domain.point.service.PointService;
import com.sparta.popupstore.domain.popupstore.bundle.entity.PopupStoreAttribute;
import com.sparta.popupstore.domain.popupstore.bundle.entity.PopupStoreOperating;
import com.sparta.popupstore.domain.popupstore.bundle.enums.PopupStoreAttributeEnum;
import com.sparta.popupstore.domain.popupstore.bundle.repository.PopupStoreAttributeRepository;
import com.sparta.popupstore.domain.popupstore.bundle.repository.PopupStoreOperatingRepository;
import com.sparta.popupstore.domain.popupstore.bundle.service.PopupStoreBundleService;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.repository.PopupStoreRepository;
import com.sparta.popupstore.domain.reservation.dto.request.ReservationCreateRequestDto;
import com.sparta.popupstore.domain.reservation.dto.response.ReservationCreateResponseDto;
import com.sparta.popupstore.domain.reservation.entity.Reservation;
import com.sparta.popupstore.domain.reservation.repository.ReservationRepository;
import com.sparta.popupstore.domain.user.entity.User;
import com.sparta.popupstore.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PopupStoreRepository popupStoreRepository;
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    PopupStoreOperatingRepository popupStoreOperatingRepository;
    @Autowired
    PopupStoreAttributeRepository popupStoreAttributeRepository;
    @Autowired
    PopupStoreBundleService popupStoreBundleService;
    @MockBean
    PointService pointService;

    @Autowired
    ReservationService reservationService;

    @Nested
    @Transactional
    @DisplayName("10명 예약 가능한 팝업스토어 예약 인원수 별 테스트")
    class NumberTest {

        Long userId = 1L;
        Long popupStoreId = 1L;
        Integer reservationLimit = 10;
        LocalDate reservationDate = LocalDate.of(2024, 12, 24);
        LocalTime reservationTime = LocalTime.of(12, 0);
        Long attributeId = 1L;
        Long operationId = 1L;

        User user = userRepository.save(
                User.builder()
                        .id(userId)
                        .point(10000)
                        .build()
        );
        PopupStore popupStore = popupStoreRepository.save(
                PopupStore.builder()
                        .id(popupStoreId)
                        .price(100)
                        .reservationLimit(reservationLimit)
                        .build()
        );
        PopupStoreAttribute attribute = popupStoreAttributeRepository.save(
                PopupStoreAttribute.builder()
                        .id(attributeId)
                        .popupStore(popupStore)
                        .attribute(PopupStoreAttributeEnum.RESERVATION)
                        .isAllow(true)
                        .build()
        );
        PopupStoreOperating operation = popupStoreOperatingRepository.save(
                PopupStoreOperating.builder()
                        .id(operationId)
                        .popupStore(popupStore)
                        .dayOfWeek(reservationDate.getDayOfWeek())
                        .startTime(reservationTime.minusHours(1))
                        .endTime(reservationTime.plusHours(1))
                        .build()
        );

        @Test
        @Transactional
        @DisplayName("5명 예약")
        void test1() {
            // given
            Integer number = 5;
            ReservationCreateRequestDto requestDto = new ReservationCreateRequestDto(
                    reservationDate,
                    reservationTime,
                    number,
                    null
            );

            doNothing().when(pointService).pointUsed(any(), any(), any(), any());

            // when
            ReservationCreateResponseDto responseDto = reservationService.createReservation(user, popupStoreId, requestDto);

            // then
            assertNotNull(responseDto);
            assertEquals(reservationDate, responseDto.getReservationDate());
            assertEquals(reservationTime, responseDto.getReservationTime());
            assertEquals(number, responseDto.getNumber());
        }


        @Test
        @Transactional
        @DisplayName("20명 예약")
        void test2() {
            // given
            Integer number = 20;
            ReservationCreateRequestDto requestDto = new ReservationCreateRequestDto(
                    reservationDate,
                    reservationTime,
                    number,
                    null
            );

            doNothing().when(pointService).pointUsed(any(), any(), any(), any());

            // when & then
            CustomApiException exception = assertThrows(CustomApiException.class,
                    () -> reservationService.createReservation(user, popupStoreId, requestDto));
            assertEquals(ErrorCode.RESERVATION_LIMIT_OVER, exception.getErrorCode());
        }

        @Test
        @Transactional
        @DisplayName("5명 예약된 상태에서 8명 예약")
        void test3() {
            // given
            Integer number1 = 5;
            Integer number2 = 8;

            reservationRepository.save(
                    Reservation.builder()
                            .id(1L)
                            .user(user)
                            .popupStore(popupStore)
                            .reservationDate(reservationDate)
                            .reservationTime(reservationTime.withMinute(30))
                            .number(number1)
                            .build()
            );
            ReservationCreateRequestDto requestDto = new ReservationCreateRequestDto(
                    reservationDate,
                    reservationTime,
                    number2,
                    null
            );

            doNothing().when(pointService).pointUsed(any(), any(), any(), any());

            // when & then
            CustomApiException exception = assertThrows(CustomApiException.class,
                    () -> reservationService.createReservation(user, popupStoreId, requestDto));
            assertEquals(ErrorCode.RESERVATION_LIMIT_OVER, exception.getErrorCode());
        }
    }
}
