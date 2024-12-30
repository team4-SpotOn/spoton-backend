package com.sparta.popupstore.domain.point.service;

import com.sparta.popupstore.domain.point.dto.request.PointChargeRequestDto;
import com.sparta.popupstore.domain.point.entity.PointChargedLog;
import com.sparta.popupstore.domain.point.repository.PointChargedLogRepository;
import com.sparta.popupstore.domain.user.entity.User;
import org.junit.jupiter.api.Assertions;
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
    void testPointCharge() {
        MockitoAnnotations.openMocks(this);
        // given
        when(pointChargedLog.getChargedPoint()).thenReturn(100); // 충전 포인트
        when(user.getPoint()).thenReturn(50); // 이전 포인트
        Integer chargedPoint = pointChargedLog.getChargedPoint();
        PointChargeRequestDto pointCharge = PointChargeRequestDto.builder()
                .user(user)
                .chargedPoint(chargedPoint)
                .prevPoint(user.getPoint())
                .build();
        //when
        pointService.pointCharge(user, pointCharge);
        when(pointChargedLogRepository.save(pointChargedLog)).thenReturn(pointChargedLog);
        //then
        Assertions.assertEquals(pointChargedLog.getChargedPoint(), chargedPoint);
    }
}
