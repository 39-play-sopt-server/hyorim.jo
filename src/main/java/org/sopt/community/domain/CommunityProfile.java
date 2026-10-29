package org.sopt.community.domain;

import jakarta.persistence.*;
import org.sopt.global.domain.BaseTimeEntity;
import org.sopt.member.domain.Profile;

import java.time.LocalDateTime;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"community_id", "profile_id"}))
public class CommunityProfile extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id", nullable = false)
    private Community community;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    private LocalDateTime deletedAt;

    protected CommunityProfile() {
    }

    public CommunityProfile(Community community, Profile profile) {
        this.community = community;
        this.profile = profile;
    }

    public Long getId() {
        return id;
    }

    public Community getCommunity() {
        return community;
    }

    public Profile getProfile() {
        return profile;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public boolean isDeleted() {
        return deletedAt != null;
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }
}
