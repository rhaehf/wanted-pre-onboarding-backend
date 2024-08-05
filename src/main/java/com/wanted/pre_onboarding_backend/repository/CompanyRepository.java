package com.wanted.pre_onboarding_backend.repository;

import com.wanted.pre_onboarding_backend.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
