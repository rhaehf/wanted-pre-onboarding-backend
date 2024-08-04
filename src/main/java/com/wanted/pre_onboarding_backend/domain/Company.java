package com.wanted.pre_onboarding_backend.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "company")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id", updatable = false)
    private Long companyId; // 회사_id

    @Column(name = "company_name")
    private String companyName; // 회사명

    @Column(name = "country")
    private String country; // 국가

    @Column(name = "region")
    private String region; // 지역

    @Builder
    public Company(Long companyId, String companyName, String country, String region) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.country = country;
        this.region = region;
    }

    @Builder
    public Company(Long companyId) {
        this.companyId = companyId;
    }
}
