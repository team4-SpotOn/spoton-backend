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

    public PointChargedLog pointCharge(User user, PointChargedLog pointChargedLog) {
        return null;
    }

    public List<PointChargedLog> pointChargeLog() {
        return null;
    }
    public List<PointUsedLog> pointUsedLogs() {
        return null;
    }
}
