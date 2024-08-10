package com.wanted.pre_onboarding_backend.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Post {

    @Id // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", updatable = false)
    private Long postId; // 채용공고_id

    @ManyToOne(fetch = FetchType.LAZY) // 일대다 연관관계 매핑
    @JoinColumn(name = "company_id") // FK
    private Company company;

    @Column(name = "position")
    private String position; // 채용포지션

    @Column(name = "signing_bonus")
    private int signingBonus; // 채용보상금

    @Column(name = "content", columnDefinition = "TEXT")
    private String content; // 채용내용

    @Column(name = "skill")
    private String skill; // 사용기술

    @Builder
    public Post(Long postId, Company company, String position, int signingBonus, String content, String skill) {
        this.postId = postId;
        this.company = company;
        this.position = position;
        this.signingBonus = signingBonus;
        this.content = content;
        this.skill = skill;
    }

    public void update(String position, int signingBonus, String content, String skill) {
        this.position = position;
        this.signingBonus = signingBonus;
        this.content = content;
        this.skill = skill;
    }
}
