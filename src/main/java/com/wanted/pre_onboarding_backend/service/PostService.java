package com.wanted.pre_onboarding_backend.service;

import com.wanted.pre_onboarding_backend.domain.Company;
import com.wanted.pre_onboarding_backend.domain.Post;
import com.wanted.pre_onboarding_backend.dto.PostRequestDto;
import com.wanted.pre_onboarding_backend.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    // 1. 채용공고 등록
    @Transactional
    public Long save(PostRequestDto postRequestDto) {
        Long companyId = postRequestDto.getCompanyId();
        Company company = new Company(companyId);

        Post post = postRepository.save(postRequestDto.toEntity(company));
        return post.getPostId();
    }
}
