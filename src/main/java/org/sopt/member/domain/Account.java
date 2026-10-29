package org.sopt.member.domain;

import jakarta.persistence.*;
import org.sopt.global.domain.BaseTimeEntity;

@Entity
public class Account extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, unique = true)
    private Member member;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    protected Account() {
    }

    public Account(Member member, String email, String password) {
        this.member = member;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
