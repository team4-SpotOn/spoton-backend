package com.sparta.popupstore.domain.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.popupstore.domain.company.entity.Company;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByEmail(String email);

    boolean existsByEmail(String email);
}
