package org.sopt.member.repository;

import org.sopt.member.domain.Account;
import org.sopt.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByEmail(String email);

    Optional<Account> findByMember(Member member);
}
