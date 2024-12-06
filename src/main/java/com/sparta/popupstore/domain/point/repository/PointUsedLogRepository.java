package com.sparta.popupstore.domain.point.repository;

import com.sparta.popupstore.domain.point.entity.PointUsedLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointUsedLogRepository extends JpaRepository<PointUsedLog, Long> {
}
