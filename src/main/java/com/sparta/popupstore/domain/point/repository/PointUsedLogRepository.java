package com.sparta.popupstore.domain.point.repository;

import com.sparta.popupstore.domain.point.entity.PointUsedLog;
import java.util.List;

import com.sparta.popupstore.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointUsedLogRepository extends JpaRepository<PointUsedLog, Long> {
  List<PointUsedLog> findAllByUser(User user);
}
