package org.sopt.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.sopt.auth.dto.LoginRequest;
import org.sopt.auth.dto.SignupRequest;
import org.sopt.auth.dto.SignupResponse;
import org.sopt.auth.dto.TokenResponse;
import org.sopt.global.code.SuccessCode;
import org.sopt.global.response.ApiResponse;
import org.sopt.global.swagger.ApiSuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "인증 API", description = "회원가입, 로그인 API 입니다")
@RequestMapping(path = "/api/v1/auth")
public interface AuthApi {
    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    @ApiSuccessCode(SuccessCode.CREATED)
    @SecurityRequirements
    ResponseEntity<ApiResponse<SignupResponse>> signup(@RequestBody @Valid SignupRequest request);

    @Operation(summary = "로그인")
    @PostMapping("/login")
    @SecurityRequirements
    ResponseEntity<ApiResponse<TokenResponse>> login(@RequestBody @Valid LoginRequest request);
}
