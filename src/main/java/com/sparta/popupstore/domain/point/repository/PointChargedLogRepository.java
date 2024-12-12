package com.sparta.popupstore.domain.point.repository;

import com.sparta.popupstore.domain.point.entity.PointChargedLog;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointChargedLogRepository extends JpaRepository<PointChargedLog, Long> {
  List<PointChargedLog> findByUserId(Long userId);
}
