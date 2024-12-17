package com.sparta.popupstore.domain.admin.repository;

import com.sparta.popupstore.domain.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findBySigninId(String signinId);
}
