package org.sopt.community.domain;

import jakarta.persistence.*;
import org.sopt.global.domain.BaseTimeEntity;

@Entity
public class Category extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id", nullable = false)
    private Community community;

    @Column(nullable = false, length = 50)
    private String name;

    protected Category() {
    }

    public Category(Community community, String name) {
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
