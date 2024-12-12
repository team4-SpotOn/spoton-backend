package com.sparta.popupstore.domain.point.service;

import com.sparta.popupstore.domain.point.dto.request.PointChargeRequestDto;
import com.sparta.popupstore.domain.point.dto.request.PointUsedRequestDto;
import com.sparta.popupstore.domain.point.dto.response.PointChargeResponseDto;
import com.sparta.popupstore.domain.point.dto.response.PointChargedLogResponseDto;
import com.sparta.popupstore.domain.point.dto.response.PointUseResponseDto;
import com.sparta.popupstore.domain.point.dto.response.PointUsedLogResponseDto;
import com.sparta.popupstore.domain.point.entity.PointChargedLog;
import com.sparta.popupstore.domain.point.entity.PointUsedLog;
import com.sparta.popupstore.domain.point.repository.PointChargedLogRepository;
import com.sparta.popupstore.domain.point.repository.PointUsedLogRepository;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
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

    public List<PointChargedLogResponseDto> pointChargeLogs(User user) {
      return pointChargedLogRepository.findByUserId(user.getId())
          .stream()
          .map(ChargedLog -> new PointChargedLogResponseDto(user ,ChargedLog))
          .toList();
    }

    public PointUseResponseDto pointUsed(User user,PointUsedRequestDto usedRequest, PopupStore popupStore) {
      user.Point(user.getPoint()-popupStore.getPrice());
      PointUsedLog usedLog = usedRequest.toEntity(user,popupStore);
      usedLog = pointUsedLogRepository.save(usedLog);
      return new PointUseResponseDto(usedLog);
    }

    public List<PointUsedLogResponseDto> pointUsedLogs(User user) {
      return pointUsedLogRepository.findByUserId(user.getId())
          .stream()
          .map(UsedLog -> new PointUsedLogResponseDto(user, UsedLog))
          .toList();
    }
}
