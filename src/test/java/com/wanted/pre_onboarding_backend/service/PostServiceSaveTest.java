package com.wanted.pre_onboarding_backend.service;

import com.wanted.pre_onboarding_backend.domain.Company;
import com.wanted.pre_onboarding_backend.domain.Post;
import com.wanted.pre_onboarding_backend.dto.PostRequestDto;
import com.wanted.pre_onboarding_backend.repository.CompanyRepository;
import com.wanted.pre_onboarding_backend.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class PostServiceSaveTest {
    @Autowired
    private PostService postService;

    @MockBean
    private CompanyRepository companyRepository;

    @MockBean
    private PostRepository postRepository;

    @Test
    @DisplayName("1. 채용공고 등록 테스트 - 정상 동작")
    void savePosts_whenValidRequest() {
        // given
        Long companyId = 1L;
        PostRequestDto postRequestDto = new PostRequestDto();
        postRequestDto.setCompanyId(companyId);

        Company company = new Company(companyId);

        // companyId로 회사를 찾을 때, 회사 객체를 반환하도록 모킹
        Mockito.when(companyRepository.findById(companyId))
                .thenReturn(Optional.of(company));

         /* 새로운 Post 객체를 생성하는 부분이 없으면
        1. post 객체가 없는 상태에서 postRepository.save(post)를 호출할 수 없다.
        post 객체가 save 메서드 호출 시 사용될 객체이기 때문에,
        Mockito.when(postRepository.save(Mockito.any(Post.class))) 설정에 영향을 미친다.
        2. postRepository.save(Mockito.any(Post.class)) 호출 시, 실제 Post 객체를 save하려고 하면 이 객체가 어떻게 생겼는지 알 수 없다.
        따라서, save 메서드에 필요한 실제 객체가 없으면 테스트의 when 부분이 제대로 동작하지 않을 수 있습니다.
        */
        // 새로운 Post 객체를 생성
        Post post = Post.builder()
                .company(company)
                .position("백엔드 시니어 개발자")
                .signingBonus(400000)
                .content("원티드랩에서 백엔드 시니어 개발자를 채용합니다. 자격요건은..")
                .skill("Java")
                .build();

        // 비교할 Post 객체
        Post savedPost = Post.builder()
                .postId(1L)
                .company(company)
                .position("백엔드 시니어 개발자")
                .signingBonus(400000)
                .content("원티드랩에서 백엔드 시니어 개발자를 채용합니다. 자격요건은..")
                .skill("Java")
                .build();

        // postRepository가 Post 객체를 save할 때, 저장된 Post 객체를 반환하도록 설정
        Mockito.when(postRepository.save(Mockito.any(Post.class))) // 어떤 Post 객체든 허용
                .thenReturn(savedPost); // savedPost를 반환하도록 설정

        // when
        Long savedPostId = postService.save(postRequestDto);

        // then
        // 반환된 savedPostId가 1L과 동일한지 확인
        assertThat(savedPostId).isEqualTo(1L);
    }
}
