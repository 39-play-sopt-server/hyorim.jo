package org.sopt.auth.service;

import org.sopt.auth.code.AuthErrorCode;
import org.sopt.auth.dto.LoginRequest;
import org.sopt.auth.dto.SignupRequest;
import org.sopt.auth.dto.SignupResponse;
import org.sopt.auth.dto.TokenResponse;
import org.sopt.global.exception.GeneralException;
import org.sopt.global.security.jwt.JwtProvider;
import org.sopt.member.domain.Account;
import org.sopt.member.domain.Member;
import org.sopt.member.repository.AccountRepository;
import org.sopt.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AuthService {
    private final MemberRepository memberRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public AuthService(MemberRepository memberRepository, AccountRepository accountRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.memberRepository = memberRepository;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    @Transactional
    public SignupResponse signup(SignupRequest request) {
        if (accountRepository.existsByEmail(request.email())) {
            throw new GeneralException(AuthErrorCode.AUTH_DUPLICATE_EMAIL);
        }

        Member member = memberRepository.save(new Member(request.nickname()));
        accountRepository.save(new Account(member, request.email(), passwordEncoder.encode(request.password())));

        String accessToken = jwtProvider.createAccessToken(member.getId());
        return SignupResponse.of(member.getId(), accessToken);
    }


    public TokenResponse login(LoginRequest request) {
        Account account = accountRepository.findByEmail(request.email())
                .orElseThrow(() -> new GeneralException(AuthErrorCode.AUTH_INVALID_CREDENTIALS));

        if (!passwordEncoder.matches(request.password(), account.getPassword())) {
            throw new GeneralException(AuthErrorCode.AUTH_INVALID_CREDENTIALS);
        }

        return TokenResponse.of(jwtProvider.createAccessToken(account.getMember().getId()));
    }
}