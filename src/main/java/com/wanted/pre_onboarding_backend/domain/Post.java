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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", updatable = false)
    private Long postId; // 채용공고_id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company; // 회사_id 를 가져오기 위한 객체

    @Column(name = "position")
    private String position; // 채용포지션

    @Column(name = "signing_bonus")
    private int signingBonus; // 채용보상금

    @Column(name = "content", columnDefinition = "TEXT")
    private String content; // 채용내용

    @Column(name = "skill")
    private String skill; // 사용기술

    @Builder
    public Post(Company company, String position, int signingBonus, String content, String skill) {
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
