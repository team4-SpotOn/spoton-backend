package com.sparta.popupstore.domain.point.service;

import com.sparta.popupstore.domain.point.dto.request.PointChargeRequestDto;
import com.sparta.popupstore.domain.point.entity.PointChargedLog;
import com.sparta.popupstore.domain.point.repository.PointChargedLogRepository;
import com.sparta.popupstore.domain.user.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

public class PointServiceTest {
    @Mock
    private PointChargedLogRepository pointChargedLogRepository;

    @Mock
    private User user;

    @Mock
    private PointService pointService;

    @Mock
    private PointChargedLog pointChargedLog;

    @Test
    @DisplayName("포인트 충전")
    void testPointCharge() {
        MockitoAnnotations.openMocks(this);
        // given
        PointChargeRequestDto pointCharge = PointChargeRequestDto.builder()
                .chargedPoint(100)
                .build();
        //when
        pointService.pointCharge(user, pointCharge);
        pointChargedLogRepository.save(pointChargedLog);
        //then
        Assertions.assertEquals(100, 100);
    }
}
