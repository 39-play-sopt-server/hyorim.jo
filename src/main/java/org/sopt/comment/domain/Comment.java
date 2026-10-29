package org.sopt.comment.domain;

import jakarta.persistence.*;
import org.sopt.community.domain.CommunityProfile;
import org.sopt.global.domain.BaseTimeEntity;
import org.sopt.post.domain.Post;

@Entity
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_profile_id", nullable = false)
    private CommunityProfile communityProfile;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    protected Comment() {
    }

    public Comment(Post post, CommunityProfile communityProfile, String content) {
        this.post = post;
        this.communityProfile = communityProfile;
        this.content = content;
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

    public String getContent() {
        return content;
    }
}
