package com.sparta.popupstore.domain.company.controller;

import com.sparta.popupstore.domain.common.annotation.AuthCompany;
import com.sparta.popupstore.domain.company.dto.request.CompanySigninRequestDto;
import com.sparta.popupstore.domain.company.dto.request.CompanySignupRequestDto;
import com.sparta.popupstore.domain.company.dto.response.CompanyMyPageResponseDto;
import com.sparta.popupstore.domain.company.dto.response.CompanyMyPopupStoreResponseDto;
import com.sparta.popupstore.domain.company.dto.response.CompanySignupResponseDto;
import com.sparta.popupstore.domain.company.entity.Company;
import com.sparta.popupstore.domain.company.service.CompanyService;
import com.sparta.popupstore.domain.user.dto.response.UserSignupResponseDto;
import com.sparta.popupstore.jwt.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Operation(summary = "회사 회원 가입", description = "회사가 회원 가입 하는 API")
    @Parameter(name = "email", description = "계정 이메일")
    @Parameter(name = "password", description = "계정 비밀번호")
    @Parameter(name = "ceoName", description = "ceo 이름")
    @Parameter(name = "name", description = "회사명")
    @Parameter(name = "address", description = "회사 주소")
    @Parameter(name = "phone", description = "회사 전화번호")
    @Parameter(name = "website", description = "회사 사이트")
    @Parameter(name = "businessLicense", description = "사업자 등록 번호")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회사 회원 가입 성공",
                    content = @Content(schema = @Schema(implementation = UserSignupResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "동일한 이메일을 가진 회사가 존재합니다."),
    })
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

    @Operation(summary = "회사 로그인", description = "회사가 로그인 하는 API")
    @Parameter(name = "email", description = "계정 이메일")
    @Parameter(name = "password", description = "계정 비밀번호")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회사 로그인 성공"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 이메일"),
            @ApiResponse(responseCode = "401", description = "비밀번호가 틀렸습니다.")
    })
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

    @Operation(summary = "회사 마이페이지", description = "회사가 로그인 후 확인하는 마이페이지")
    @GetMapping("/mypage")
    public ResponseEntity<CompanyMyPageResponseDto> getCompanyMyPage(@AuthCompany Company company) {
        return ResponseEntity.ok(companyService.getCompanyMyPage(company));
    }

    @Operation(summary = "회사 자사 팝업스토어 조회", description = "회사가 본인이 등록한 팝업스토어 목록 조회")
    @GetMapping("/popupstores")
    public ResponseEntity<List<CompanyMyPopupStoreResponseDto>> getCompanyMyPopupStore(@AuthCompany Company company){
        return ResponseEntity.ok(companyService.getCompanyMyPopupStore(company));
    }







}
