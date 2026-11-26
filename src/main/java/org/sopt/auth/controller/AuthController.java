package org.sopt.auth.controller;

import org.sopt.auth.dto.AuthTokens;
import org.sopt.auth.dto.LoginRequest;
import org.sopt.auth.dto.SignupRequest;
import org.sopt.auth.dto.SignupResponse;
import org.sopt.auth.dto.TokenResponse;
import org.sopt.auth.service.AuthService;
import org.sopt.global.response.ApiResponse;
import org.sopt.global.security.jwt.RefreshTokenCookieProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController implements AuthApi {
    private final AuthService authService;
    private final RefreshTokenCookieProvider refreshTokenCookieProvider;

    public AuthController(AuthService authService, RefreshTokenCookieProvider refreshTokenCookieProvider) {
        this.authService = authService;
        this.refreshTokenCookieProvider = refreshTokenCookieProvider;
    }

    @Override
    public ResponseEntity<ApiResponse<SignupResponse>> signup(SignupRequest request) {
        AuthTokens tokens = authService.signup(request);
        return withCookie(
                ApiResponse.created(SignupResponse.of(tokens.memberId(), tokens.accessToken())),
                refreshTokenCookieProvider.create(tokens.refreshToken())
        );
    }

    @Override
    public ResponseEntity<ApiResponse<TokenResponse>> login(LoginRequest request) {
        return withTokens(authService.login(request));
    }

    @Override
    public ResponseEntity<ApiResponse<TokenResponse>> reissue(String refreshToken) {
        return withTokens(authService.reissue(refreshToken));
    }

    @Override
    public ResponseEntity<ApiResponse<Void>> logout(Long memberId) {
        authService.logout(memberId);
        return withCookie(ApiResponse.ok(), refreshTokenCookieProvider.expire());
    }

    private ResponseEntity<ApiResponse<TokenResponse>> withTokens(AuthTokens tokens) {
        return withCookie(
                ApiResponse.ok(TokenResponse.of(tokens.accessToken())),
                refreshTokenCookieProvider.create(tokens.refreshToken())
        );
    }

    private static <T> ResponseEntity<T> withCookie(ResponseEntity<T> response, ResponseCookie cookie) {
        return ResponseEntity.status(response.getStatusCode())
                .headers(response.getHeaders())
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(response.getBody());
    }
}
