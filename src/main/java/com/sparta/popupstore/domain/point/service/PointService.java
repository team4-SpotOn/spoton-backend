package com.sparta.popupstore.domain.point.service;

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

    public PointChargedLog pointCharge(User user, PointChargedLog chargeLog) {
       int totalPoint = totalPoint(user);

       PointChargedLog pointChargedLog = new PointChargedLog(
           chargeLog.getId(),
           chargeLog.getUser(),
           chargeLog.getPrevPoint(),
           chargeLog.getChargedPoint(),
           chargeLog.getChargedAt()
       );

       return pointChargedLogRepository.save(pointChargedLog);
    }

    public List<PointChargedLog> pointChargeLogs(PointChargedLog chargeLog) {
        return pointChargedLogRepository.findByChargedPoint(chargeLog.getId());
    }

    public List<PointUsedLog> pointUsedLogs(PointUsedLog usedLog) {
        return pointUsedLogRepository.findByUsedPoint(usedLog.getId());
    }

    private int totalPoint(User user){
        int totalCharged = pointChargedLogRepository.findByChargedPoint(user.getId())
            .stream()
            .mapToInt(PointChargedLog::getChargedPoint)
            .sum();

        int totalUsed = pointUsedLogRepository.findByUsedPoint(user.getId())
            .stream()
            .mapToInt(PointUsedLog::getUsedPoint)
            .sum();

        return totalCharged - totalUsed;
    }
}
