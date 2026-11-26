package org.sopt.auth.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.sopt.global.domain.BaseTimeEntity;
import org.sopt.member.domain.Member;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HexFormat;

@Entity
@Table(indexes = @Index(name = "idx_refresh_token_previous_hash", columnList = "previous_token_hash"))
public class RefreshToken extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, unique = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @Column(name = "token", nullable = false, unique = true, length = 64)
    private String tokenHash;

    @Column(name = "previous_token_hash", length = 64)
    private String previousTokenHash;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    protected RefreshToken() {
    }

    public RefreshToken(Member member, String rawToken, LocalDateTime expiresAt) {
        this.member = member;
        this.tokenHash = hash(rawToken);
        this.expiresAt = expiresAt;
    }

    // 로그인
    public void renew(String rawToken, LocalDateTime expiresAt) {
        this.tokenHash = hash(rawToken);
        this.previousTokenHash = null;
        this.expiresAt = expiresAt;
    }

    // 재발급
    public void rotate(String rawToken, LocalDateTime expiresAt) {
        this.previousTokenHash = this.tokenHash;
        this.tokenHash = hash(rawToken);
        this.expiresAt = expiresAt;
    }

    public boolean isExpired(LocalDateTime now) {
        return !expiresAt.isAfter(now);
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public static String hash(String rawToken) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256")
                    .digest(rawToken.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("토큰 해싱 실패", e);
        }
    }
}
