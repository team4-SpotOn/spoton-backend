package com.sparta.popupstore.domain.point.service;

import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.domain.point.dto.request.PointChargeRequestDto;
import com.sparta.popupstore.domain.point.dto.request.PointUseRequestDto;
import com.sparta.popupstore.domain.point.dto.response.PointChargeResponseDto;
import com.sparta.popupstore.domain.point.dto.response.PointChargedLogResponseDto;
import com.sparta.popupstore.domain.point.dto.response.PointUseResponseDto;
import com.sparta.popupstore.domain.point.dto.response.PointUsedLogResponseDto;
import com.sparta.popupstore.domain.point.entity.PointChargedLog;
import com.sparta.popupstore.domain.point.entity.PointUsedLog;
import com.sparta.popupstore.domain.point.repository.PointChargedLogRepository;
import com.sparta.popupstore.domain.point.repository.PointUsedLogRepository;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.popupstore.repository.PopupStoreRepository;
import com.sparta.popupstore.domain.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointService {

    private final PointChargedLogRepository pointChargedLogRepository;
    private final PointUsedLogRepository pointUsedLogRepository;
    private final PopupStoreRepository popupStoreRepository;

    public PointChargeResponseDto pointCharge(User user, PointChargeRequestDto chargeRequest) {
      PointChargedLog chargedLog = chargeRequest.toEntity(user);
      chargedLog = pointChargedLogRepository.save(chargedLog);
      user.ChargePoint(chargeRequest.getChargedPoint());
      return new PointChargeResponseDto(chargedLog);
    }

    public List<PointChargedLogResponseDto> pointChargeLogs(User user) {
      return pointChargedLogRepository.findAllByUser(user)
          .stream()
          .map(PointChargedLogResponseDto::new)
          .toList();
    }

    public PointUseResponseDto pointUsed(User user, PointUseRequestDto usedRequest, Long popupStoreId) {
        PopupStore popupStore = popupStoreRepository.findById(popupStoreId)
            .orElseThrow(() -> new CustomApiException(ErrorCode.POPUP_STORE_NOT_FOUND));

        if (user.getPoint() < popupStore.getPrice()) {
            throw new CustomApiException(ErrorCode.NOT_ENOUGH_POINT);
        }

        user.ChargePoint(- popupStore.getPrice());
        PointUsedLog usedLog = usedRequest.toEntity(user, popupStore);
        usedLog = pointUsedLogRepository.save(usedLog);

        return new PointUseResponseDto(usedLog);
    }

    public List<PointUsedLogResponseDto> pointUsedLogs(User user) {
      return pointUsedLogRepository.findAllByUser(user)
          .stream()
          .map(PointUsedLogResponseDto::new)
          .toList();
    }
}
