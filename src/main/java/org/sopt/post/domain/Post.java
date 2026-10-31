package org.sopt.post.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.sopt.community.domain.Board;
import org.sopt.community.domain.CommunityProfile;
import org.sopt.community.domain.Prefix;
import org.sopt.global.domain.BaseTimeEntity;

@Entity
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prefix_id")
    private Prefix prefix;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_profile_id", nullable = false)
    private CommunityProfile communityProfile;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ColumnDefault("0")
    @Column(nullable = false)
    private long viewCount;

    protected Post() {
    }

    public Post(Board board, Prefix prefix, CommunityProfile communityProfile, String title, String content) {
        this.board = board;
        this.prefix = prefix;
        this.communityProfile = communityProfile;
        this.title = title;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public Board getBoard() {
        return board;
    }

    public Prefix getPrefix() {
        return prefix;
    }

    public CommunityProfile getCommunityProfile() {
        return communityProfile;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public long getViewCount() {
        return viewCount;
    }

    // Post 수정은 제목과 내용을 모두 입력 받는다.
    public void updatePost(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
