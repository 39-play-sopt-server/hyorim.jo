package org.sopt.auth.controller;

import org.sopt.auth.dto.LoginRequest;
import org.sopt.auth.dto.SignupRequest;
import org.sopt.auth.dto.SignupResponse;
import org.sopt.auth.dto.TokenResponse;
import org.sopt.auth.service.AuthService;
import org.sopt.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController implements AuthApi {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public ResponseEntity<ApiResponse<SignupResponse>> signup(SignupRequest request) {
        return ApiResponse.created(authService.signup(request));
    }

    @Override
    public ResponseEntity<ApiResponse<TokenResponse>> login(LoginRequest request) {
        return ApiResponse.ok(authService.login(request));
    }
}
