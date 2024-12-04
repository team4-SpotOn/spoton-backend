package com.sparta.popupstore.domain.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.popupstore.domain.company.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    boolean existsByEmail(String email);
}
