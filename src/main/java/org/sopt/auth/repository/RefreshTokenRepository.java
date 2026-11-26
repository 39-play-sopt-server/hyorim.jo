package org.sopt.auth.repository;

import org.sopt.auth.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByMemberId(Long memberId);

    Optional<RefreshToken> findByTokenHash(String tokenHash);

    Optional<RefreshToken> findByPreviousTokenHash(String previousTokenHash);

    void deleteByMemberId(Long memberId);
}
