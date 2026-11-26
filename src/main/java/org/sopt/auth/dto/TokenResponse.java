package org.sopt.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record TokenResponse(
        @Schema(description = "토큰 타입", example = "Bearer")
        String tokenType,

        @Schema(description = "액세스 토큰", example = "eyJhbGciOiJIUzI1NiJ9...")
        String accessToken
) {
    public static TokenResponse of(String accessToken) {
        return new TokenResponse("Bearer", accessToken);
    }
}
