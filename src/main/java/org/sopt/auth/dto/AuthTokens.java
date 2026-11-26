package org.sopt.auth.dto;

public record AuthTokens(
        Long memberId,
        String accessToken,
        String refreshToken
) {
}
