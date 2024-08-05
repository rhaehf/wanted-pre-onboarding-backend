package com.wanted.pre_onboarding_backend.dto;

import com.wanted.pre_onboarding_backend.domain.Post;
import lombok.Getter;

@Getter
public class PostResponseDto {
    private Long postId; // 채용공고_id
    private String companyName; // 회사명
    private String country; // 국가
    private String region; // 지역
    private String position; // 채용포지션
    private int signingBonus; // 채용보상금
    private String skill; // 사용기술

    public PostResponseDto(Post post) {
        this.postId = post.getPostId();
        this.companyName = post.getCompany().getCompanyName();
        this.country = post.getCompany().getCountry();
        this.region = post.getCompany().getRegion();
        this.position = post.getPosition();
        this.signingBonus = post.getSigningBonus();
        this.skill = post.getSkill();
    }
}
