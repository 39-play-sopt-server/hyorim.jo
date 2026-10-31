package org.sopt.member.repository;

import org.sopt.member.domain.Member;
import org.sopt.member.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    boolean existsByMember(Member member);
}
