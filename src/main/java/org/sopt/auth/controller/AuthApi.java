package org.sopt.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.sopt.auth.dto.LoginRequest;
import org.sopt.auth.dto.SignupRequest;
import org.sopt.auth.dto.SignupResponse;
import org.sopt.auth.dto.TokenResponse;
import org.sopt.global.code.SuccessCode;
import org.sopt.global.response.ApiResponse;
import org.sopt.global.security.jwt.RefreshTokenCookieProvider;
import org.sopt.global.swagger.ApiSuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "인증 API", description = "Refresh Token은 쿠키로 발급")
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

    @Operation(summary = "토큰 재발급", description = "두 토큰을 모두 새로 발급합니다. 사용한 Refresh Token은 무효가 됩니다.")
    @PostMapping("/reissue")
    @SecurityRequirements
    ResponseEntity<ApiResponse<TokenResponse>> reissue(
            @Parameter(hidden = true)
            @CookieValue(name = RefreshTokenCookieProvider.COOKIE_NAME, required = false) String refreshToken
    );

    @Operation(summary = "로그아웃", description = "저장된 Refresh Token을 삭제하고 쿠키를 만료시킵니다.")
    @PostMapping("/logout")
    ResponseEntity<ApiResponse<Void>> logout(
            @Parameter(hidden = true)
            @AuthenticationPrincipal Long memberId
    );
}
