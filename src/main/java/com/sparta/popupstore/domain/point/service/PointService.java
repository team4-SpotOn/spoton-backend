package com.sparta.popupstore.domain.point.service;

import com.sparta.popupstore.domain.point.dto.request.PointChargeRequestDto;
import com.sparta.popupstore.domain.point.dto.response.PointChargeResponseDto;
import com.sparta.popupstore.domain.point.entity.PointChargedLog;
import com.sparta.popupstore.domain.point.entity.PointUsedLog;
import com.sparta.popupstore.domain.point.repository.PointChargedLogRepository;
import com.sparta.popupstore.domain.point.repository.PointUsedLogRepository;
import com.sparta.popupstore.domain.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointService {

    private final PointChargedLogRepository pointChargedLogRepository;
    private final PointUsedLogRepository pointUsedLogRepository;

    public PointChargeResponseDto pointCharge(User user, PointChargeRequestDto chargeRequest) {
      PointChargedLog chargedLog = chargeRequest.toEntity(user);
      user.Point(chargeRequest.getChargedPoint());
      chargedLog = pointChargedLogRepository.save(chargedLog);
      return new PointChargeResponseDto(chargedLog);
    }

    public List<PointChargedLog> pointChargeLogs(User user) {
      return pointChargedLogRepository.findByUserId(user.getId());
    }

    public List<PointUsedLog> pointUsedLogs(PointUsedLog usedLog) {
        return pointUsedLogRepository.findByUsedPoint(usedLog.getId());
    }
}
