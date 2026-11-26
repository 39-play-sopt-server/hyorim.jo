package org.sopt.global.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtProvider {

    private static final String TOKEN_TYPE_CLAIM = "type";
    private static final String ACCESS_TOKEN_TYPE = "ACCESS";

    private final SecretKey secretKey;
    private final Duration accessTokenExpiration;

    public JwtProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-expiration}") long accessTokenExpirationMillis
    ) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.accessTokenExpiration = Duration.ofMillis(accessTokenExpirationMillis);
    }

    public String createAccessToken(Long memberId) {
        Instant now = Instant.now();

        return Jwts.builder()
                .subject(String.valueOf(memberId))   // 누구의 토큰인지
                .claim(TOKEN_TYPE_CLAIM, ACCESS_TOKEN_TYPE)
                .issuedAt(Date.from(now))            // 발급 시각
                .expiration(Date.from(now.plus(accessTokenExpiration))) // 만료 시각
                .signWith(secretKey)                 // 서명
                .compact();
    }

    public Long getMemberId(String accessToken) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(accessToken) // 서명, 만료 검증 → 실패 시 JwtException
                .getPayload();

        String tokenType = claims.get(TOKEN_TYPE_CLAIM, String.class);
        if (!ACCESS_TOKEN_TYPE.equals(tokenType)) {
            throw new JwtException("Access Token이 아닙니다. type=" + tokenType);
        }

        return Long.valueOf(claims.getSubject());
    }
}
