package com.wanted.pre_onboarding_backend.dto;

import com.wanted.pre_onboarding_backend.domain.Company;
import com.wanted.pre_onboarding_backend.domain.Post;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequestDto {

    @NotNull(message = "companyId는 필수 입력값입니다.") // null을 허용하지 않음, ""과 " "은 혀용함
    private Long companyId; // 회사_id

    private String position; // 채용포지션
    private int signingBonus; // 채용보상금
    private String content; // 채용내용
    private String skill; // 사용기술

    public Post toEntity(Company company) {
        return Post.builder()
                .company(company)
                .position(position)
                .signingBonus(signingBonus)
                .content(content)
                .skill(skill)
                .build();
    }
}
