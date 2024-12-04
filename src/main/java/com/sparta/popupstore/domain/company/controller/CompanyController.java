package com.sparta.popupstore.domain.company.controller;

import com.sparta.popupstore.domain.company.dto.request.CompanySigninRequestDto;
import com.sparta.popupstore.domain.company.dto.request.CompanySignupRequestDto;
import com.sparta.popupstore.domain.company.dto.response.CompanySignupResponseDto;
import com.sparta.popupstore.domain.company.entity.Company;
import com.sparta.popupstore.domain.company.service.CompanyService;
import com.sparta.popupstore.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<CompanySignupResponseDto> signup(
            @RequestBody @Valid CompanySignupRequestDto requestDto,
            HttpServletResponse response
    ) {
        CompanySignupResponseDto responseDto = companyService.signup(requestDto);
        jwtUtil.addJwtToCookie(responseDto.getEmail(), response);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDto);
    }

    @PostMapping("/signin")
    public ResponseEntity<Void> signin(
            @RequestBody CompanySigninRequestDto requestDto,
            HttpServletResponse response
    ) {
        Company company = companyService.signin(requestDto);
        jwtUtil.addJwtToCookie(company.getEmail(), response);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
