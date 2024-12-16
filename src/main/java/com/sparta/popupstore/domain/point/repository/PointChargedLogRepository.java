package com.sparta.popupstore.domain.point.repository;

import com.sparta.popupstore.domain.point.entity.PointChargedLog;
import java.util.List;

import com.sparta.popupstore.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointChargedLogRepository extends JpaRepository<PointChargedLog, Long> {
  List<PointChargedLog> findAllByUser(User user);
}
