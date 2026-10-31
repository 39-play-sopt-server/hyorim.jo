package org.sopt.community.domain;

import jakarta.persistence.*;
import org.sopt.global.domain.BaseTimeEntity;

@Entity
public class Prefix extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id", nullable = false)
    private Community community;

    @Column(nullable = false, length = 20)
    private String name;

    protected Prefix() {
    }

    public Prefix(Community community, String name) {
        this.community = community;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public Community getCommunity() {
        return community;
    }

    public String getName() {
        return name;
    }
}
