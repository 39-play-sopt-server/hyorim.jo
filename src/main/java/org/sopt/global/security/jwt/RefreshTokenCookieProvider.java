package org.sopt.global.security.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RefreshTokenCookieProvider {
    public static final String COOKIE_NAME = "refresh_token";
    private static final String COOKIE_PATH = "/api/v1/auth";

    private final Duration maxAge;
    private final String sameSite;
    private final boolean secure;

    public RefreshTokenCookieProvider(
            @Value("${jwt.refresh-token-expiration}") long refreshTokenExpirationMillis,
            @Value("${jwt.refresh-cookie.same-site:Strict}") String sameSite,
            @Value("${jwt.refresh-cookie.secure:true}") boolean secure
    ) {
        this.maxAge = Duration.ofMillis(refreshTokenExpirationMillis);
        this.sameSite = sameSite;
        this.secure = secure;
    }

    public ResponseCookie create(String refreshToken) {
        return build(refreshToken, maxAge);
    }

    public ResponseCookie expire() {
        return build("", Duration.ZERO);
    }

    private ResponseCookie build(String value, Duration maxAge) {
        return ResponseCookie.from(COOKIE_NAME, value)
                .httpOnly(true)
                .secure(secure)
                .sameSite(sameSite)
                .path(COOKIE_PATH)
                .maxAge(maxAge)
                .build();
    }
}
