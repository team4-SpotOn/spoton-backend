package com.sparta.popupstore.domain.point.service;

import com.sparta.popupstore.domain.common.exception.CustomApiException;
import com.sparta.popupstore.domain.common.exception.ErrorCode;
import com.sparta.popupstore.domain.coupon.entity.Coupon;
import com.sparta.popupstore.domain.coupon.repository.CouponRepository;
import com.sparta.popupstore.domain.point.dto.request.PointChargeRequestDto;
import com.sparta.popupstore.domain.point.dto.response.PointChargeResponseDto;
import com.sparta.popupstore.domain.point.dto.response.PointChargedLogResponseDto;
import com.sparta.popupstore.domain.point.dto.response.PointUsedLogResponseDto;
import com.sparta.popupstore.domain.point.entity.PointChargedLog;
import com.sparta.popupstore.domain.point.entity.PointUsedLog;
import com.sparta.popupstore.domain.point.repository.PointChargedLogRepository;
import com.sparta.popupstore.domain.point.repository.PointUsedLogRepository;
import com.sparta.popupstore.domain.popupstore.entity.PopupStore;
import com.sparta.popupstore.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PointService {

    private final PointChargedLogRepository pointChargedLogRepository;
    private final PointUsedLogRepository pointUsedLogRepository;
    private final CouponRepository couponRepository;

    public PointChargeResponseDto pointCharge(User user, PointChargeRequestDto chargeRequest) {
        PointChargedLog chargedLog = chargeRequest.toEntity(user);
        chargedLog = pointChargedLogRepository.save(chargedLog);
        user.chargePoint(chargeRequest.getChargedPoint());
        return new PointChargeResponseDto(chargedLog);
    }

    public List<PointChargedLogResponseDto> pointChargeLogs(User user) {
        return pointChargedLogRepository.findAllByUser(user)
                .stream()
                .map(PointChargedLogResponseDto::new)
                .toList();
    }

    public void pointUsed(User user, PopupStore popupStore, Integer number, String couponSerialNumber) {
        int discountPercentage = couponRepository.findBySerialNumber(couponSerialNumber)
                .orElseGet(() -> Coupon.builder().discountPercentage(0).build())
                .getDiscountPercentage();
        int amount = popupStore.getPrice() * number * (100 - discountPercentage) / 100;
        if(user.getPoint() < amount) {
            throw new CustomApiException(ErrorCode.NOT_ENOUGH_POINT);
        }

        pointUsedLogRepository.save(
                PointUsedLog.builder()
                        .user(user)
                        .popupStoreId(popupStore.getId())
                        .prevPoint(user.getPoint())
                        .usedPoint(amount)
                        .couponSerialNumber(couponSerialNumber)
                        .build()
        );
        user.decreasePoint(amount);
    }

    public List<PointUsedLogResponseDto> pointUsedLogs(User user) {
        return pointUsedLogRepository.findAllByUser(user)
                .stream()
                .map(PointUsedLogResponseDto::new)
                .toList();
    }
}
