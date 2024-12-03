package com.sparta.popupstore.domain.user.repository;

import com.sparta.popupstore.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    // 마이 쿠폰 구하기
    @Query(value = "SELECT id FROM coupons WHERE user_id = :userId", nativeQuery = true)
    List<Object[]> findByUserCoupons(Long userId);
}
