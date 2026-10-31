package org.sopt.community.repository;

import org.sopt.community.domain.Community;
import org.sopt.community.domain.CommunityProfile;
import org.sopt.member.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommunityProfileRepository extends JpaRepository<CommunityProfile, Long> {
    Optional<CommunityProfile> findByCommunityAndProfile(Community community, Profile profile);
}
