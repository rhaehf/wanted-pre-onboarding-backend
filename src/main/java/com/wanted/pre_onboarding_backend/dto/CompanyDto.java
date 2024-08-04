package com.wanted.pre_onboarding_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDto {
    private Long companyId; // 회사_id
    private String companyName; // 회사명
    private String country; // 국가
    private String region; // 지역
}
