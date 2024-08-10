package com.wanted.pre_onboarding_backend.service;

import com.wanted.pre_onboarding_backend.domain.Company;
import com.wanted.pre_onboarding_backend.domain.Post;
import com.wanted.pre_onboarding_backend.dto.PostRequestDto;
import com.wanted.pre_onboarding_backend.exception.CustomException;
import com.wanted.pre_onboarding_backend.exception.ErrorCode;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @Test
    @DisplayName("1-1. companyId를 입력하지 않고 데이터를 넣을 때")
    void savePosts_whenCompanyIdIsNull() {
        // given
        PostRequestDto postRequestDto = new PostRequestDto();
        postRequestDto.setCompanyId(null);

        // when & then
        // postService.save 메서드를 호출할 때, companyId가 null인 경우 CustomException이 발생하는지 확인
        assertThatThrownBy(() -> postService.save(postRequestDto))
                .isInstanceOf(CustomException.class) // CustomException이 발생해야 한다.
                .hasMessageContaining("companyId가 누락되어 채용공고를 등록할 수 없습니다.") // 예외 메시지에 특정 텍스트가 포함되어야 한다.
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.INVALID_INPUT_VALUE); // 예외의 errorCode가 INVALID_INPUT_VALUE여야 한다.
    }

    @Test
    @DisplayName("1-2. 존재하지 않는 companyId를 사용할 때")
    void savePosts_whenCompanyNotFound() {
        // given
        // 존재하지 않는 companyId를 설정하여 PostRequestDto를 생성
        Long companyId = 4L;
        PostRequestDto postRequestDto = new PostRequestDto();
        postRequestDto.setCompanyId(companyId);

        // companyRepository의 findById 메서드가 빈 Optional을 반환하도록 모킹
        Mockito.when(companyRepository.findById(companyId))
                .thenReturn(Optional.empty());

        // when & then
        // companyId는 유효하지만 해당 회사는 존재하지 않는다.
        assertThatThrownBy(() -> postService.save(postRequestDto))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining("해당 companyId로 회사를 찾을 수 없습니다.")
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.COMPANY_NOT_FOUND);
    }
}