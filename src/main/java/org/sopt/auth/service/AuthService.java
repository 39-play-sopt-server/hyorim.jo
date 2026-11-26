package org.sopt.auth.service;

import org.sopt.auth.code.AuthErrorCode;
import org.sopt.auth.domain.RefreshToken;
import org.sopt.auth.dto.AuthTokens;
import org.sopt.auth.dto.LoginRequest;
import org.sopt.auth.dto.SignupRequest;
import org.sopt.auth.repository.RefreshTokenRepository;
import org.sopt.global.exception.GeneralException;
import org.sopt.global.security.TokenErrorCode;
import org.sopt.global.security.jwt.JwtProvider;
import org.sopt.member.domain.Account;
import org.sopt.member.domain.Member;
import org.sopt.member.repository.AccountRepository;
import org.sopt.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AuthService {
    private static final BytesKeyGenerator REFRESH_TOKEN_GENERATOR = KeyGenerators.secureRandom(32);

    private final MemberRepository memberRepository;
    private final AccountRepository accountRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final Duration refreshTokenExpiration;

    public AuthService(MemberRepository memberRepository, AccountRepository accountRepository,
                       RefreshTokenRepository refreshTokenRepository, PasswordEncoder passwordEncoder,
                       JwtProvider jwtProvider,
                       @Value("${jwt.refresh-token-expiration}") long refreshTokenExpirationMillis) {
        this.memberRepository = memberRepository;
        this.accountRepository = accountRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.refreshTokenExpiration = Duration.ofMillis(refreshTokenExpirationMillis);
    }

    @Transactional
    public AuthTokens signup(SignupRequest request) {
        if (accountRepository.existsByEmail(request.email())) {
            throw new GeneralException(AuthErrorCode.AUTH_DUPLICATE_EMAIL);
        }

        Member member = memberRepository.save(new Member(request.nickname()));
        accountRepository.save(new Account(member, request.email(), passwordEncoder.encode(request.password())));

        return issueLoginTokens(member);
    }

    @Transactional
    public AuthTokens login(LoginRequest request) {
        Account account = accountRepository.findByEmail(request.email())
                .orElseThrow(() -> new GeneralException(AuthErrorCode.AUTH_INVALID_CREDENTIALS));

        if (!passwordEncoder.matches(request.password(), account.getPassword())) {
            throw new GeneralException(AuthErrorCode.AUTH_INVALID_CREDENTIALS);
        }

        return issueLoginTokens(account.getMember());
    }

    @Transactional(noRollbackFor = GeneralException.class)
    public AuthTokens reissue(String refreshToken) {
        if (refreshToken == null || refreshToken.isBlank()) {
            throw new GeneralException(TokenErrorCode.TOKEN_REFRESH_MISSING);
        }

        String tokenHash = RefreshToken.hash(refreshToken);
        Optional<RefreshToken> current = refreshTokenRepository.findByTokenHash(tokenHash);

        // 재사용 토큰 삭제
        if (current.isEmpty()) {
            refreshTokenRepository.findByPreviousTokenHash(tokenHash)
                    .ifPresent(refreshTokenRepository::delete);
            throw new GeneralException(TokenErrorCode.TOKEN_REFRESH_INVALID);
        }

        RefreshToken saved = current.get();
        if (saved.isExpired(LocalDateTime.now())) {
            refreshTokenRepository.delete(saved);
            throw new GeneralException(TokenErrorCode.TOKEN_REFRESH_INVALID);
        }

        String newRefreshToken = generateRefreshToken();
        saved.rotate(newRefreshToken, nextExpiresAt());

        Long memberId = saved.getMember().getId();
        return new AuthTokens(memberId, jwtProvider.createAccessToken(memberId), newRefreshToken);
    }

    @Transactional
    public void logout(Long memberId) {
        refreshTokenRepository.deleteByMemberId(memberId);
    }

    // 회원가입, 로그인
    private AuthTokens issueLoginTokens(Member member) {
        String refreshToken = generateRefreshToken();
        LocalDateTime expiresAt = nextExpiresAt();

        refreshTokenRepository.findByMemberId(member.getId())
                .ifPresentOrElse(saved -> saved.renew(refreshToken, expiresAt),
                        () -> refreshTokenRepository.save(new RefreshToken(member, refreshToken, expiresAt))
                );

        return new AuthTokens(member.getId(), jwtProvider.createAccessToken(member.getId()), refreshToken);
    }

    private LocalDateTime nextExpiresAt() {
        return LocalDateTime.now().plus(refreshTokenExpiration);
    }

    private static String generateRefreshToken() {
        return Base64.getUrlEncoder().withoutPadding()
                .encodeToString(REFRESH_TOKEN_GENERATOR.generateKey());
    }
}
