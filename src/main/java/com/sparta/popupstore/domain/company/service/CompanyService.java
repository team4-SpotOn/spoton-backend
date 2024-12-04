package com.sparta.popupstore.domain.company.service;

import com.sparta.popupstore.config.PasswordEncoder;
import com.sparta.popupstore.domain.company.dto.request.CompanySigninRequestDto;
import com.sparta.popupstore.domain.company.dto.request.CompanySignupRequestDto;
import com.sparta.popupstore.domain.company.dto.response.CompanyMyPageResponseDto;
import com.sparta.popupstore.domain.company.dto.response.CompanySignupResponseDto;
import com.sparta.popupstore.domain.company.entity.Company;
import com.sparta.popupstore.domain.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;

    public CompanySignupResponseDto signup(CompanySignupRequestDto requestDto) {
        if(companyRepository.existsByEmail(requestDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        Company company = requestDto.toEntity(encodedPassword);
        return new CompanySignupResponseDto(companyRepository.save(company));
    }

    public Company signin(CompanySigninRequestDto requestDto) {
        Company company = companyRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new RuntimeException("Email address not found"));
        if(!passwordEncoder.matches(requestDto.getPassword(), company.getPassword())) {
            throw new RuntimeException("Incorrect password");
        }

        return company;
    }

    // 회사 마이페이지
    public CompanyMyPageResponseDto getCompanyMyPage(Company company){
        System.out.println(company.getId());
        return new CompanyMyPageResponseDto(company);
    }

}
