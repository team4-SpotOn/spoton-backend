package com.sparta.popupstore.domain.user.repository;

import com.sparta.popupstore.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    Optional<User> findByEmailAndDeletedAtIsNull(String email);

    Optional<User> findByQrCode(String qrCode);

    Optional<User> findByPhone(String phone);
}
