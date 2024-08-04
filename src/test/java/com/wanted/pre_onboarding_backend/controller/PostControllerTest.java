package com.wanted.pre_onboarding_backend.controller;

import com.wanted.pre_onboarding_backend.domain.Company;
import com.wanted.pre_onboarding_backend.domain.Post;
import com.wanted.pre_onboarding_backend.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PostControllerTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    @DisplayName("1. 채용공고 등록 테스트 - 모든 데이터를 입력했을 때")
    void savePosts() {
        //given
        Long companyId = 1L;
        Company company = new Company(companyId);
        Post build = Post.builder()
                .company(company)
                .position("백엔드 주니어 개발자").signingBonus(1000000)
                .content("원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..").skill("Python")
                .build();
        postRepository.save(build);

        // when
        List<Post> postsList = postRepository.findAll();

        // then
        Post post = postsList.get(0);
        assertThat(post.getCompany().getCompanyId()).isEqualTo(companyId);
    }
}