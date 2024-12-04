package com.sparta.popupstore.domain.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.popupstore.domain.company.entity.Company;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    boolean existsByEmail(String email);

    Optional<Company> findByEmail(String email);
}
