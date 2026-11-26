package org.sopt.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record SignupResponse(
        @Schema(description = "회원 ID", example = "1")
        Long memberId,

        @Schema(description = "토큰 타입", example = "Bearer")
        String tokenType,

        @Schema(description = "액세스 토큰", example = "eyJhbGciOiJIUzI1NiJ9...")
        String accessToken
) {
    public static SignupResponse of(Long memberId, String accessToken) {
        return new SignupResponse(memberId, "Bearer", accessToken);
    }
}
