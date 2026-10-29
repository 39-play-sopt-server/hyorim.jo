package org.sopt.like.domain;

import jakarta.persistence.*;
import org.sopt.community.domain.CommunityProfile;
import org.sopt.global.domain.BaseTimeEntity;
import org.sopt.post.domain.Post;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"post_id", "community_profile_id"}))
public class PostLike extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_profile_id", nullable = false)
    private CommunityProfile communityProfile;

    protected PostLike() {
    }

    public PostLike(Post post, CommunityProfile communityProfile) {
        this.post = post;
        this.communityProfile = communityProfile;
    }

    public Long getId() {
        return id;
    }

    public Post getPost() {
        return post;
    }

    public CommunityProfile getCommunityProfile() {
        return communityProfile;
    }
}
