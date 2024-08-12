package com.wanted.pre_onboarding_backend.service;

import com.wanted.pre_onboarding_backend.domain.Company;
import com.wanted.pre_onboarding_backend.domain.Post;
import com.wanted.pre_onboarding_backend.dto.PostRequestDto;
import com.wanted.pre_onboarding_backend.dto.PostResponseDto;
import com.wanted.pre_onboarding_backend.exception.CustomException;
import com.wanted.pre_onboarding_backend.exception.ErrorCode;
import com.wanted.pre_onboarding_backend.repository.CompanyRepository;
import com.wanted.pre_onboarding_backend.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final CompanyRepository companyRepository;

    // 1. 채용공고 등록
    @Transactional
    public Long save(PostRequestDto postRequestDto) {
        Long companyId = postRequestDto.getCompanyId();
        if (companyId == null) {
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE, "companyId가 누락되어 채용공고를 등록할 수 없습니다.");
        }

//        Company company = companyRepository.findById(companyId).orElseThrow(() -> new EntityNotFoundException("해당 companyId로 회사를 찾을 수 없습니다."));
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new CustomException(ErrorCode.COMPANY_NOT_FOUND));
        Post post = postRepository.save(postRequestDto.toEntity(company));
        return post.getPostId();
    }

    // 2. 채용공고 수정
    @Transactional
    public Long update(Long id, PostRequestDto dto) {
//        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("해당 채용공고를 찾을 수 없습니다."));
        Post post = postRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));
        post.update(dto.getPosition(), dto.getSigningBonus(), dto.getContent(), dto.getSkill());
        return id;
    }

    // 3. 채용공고 삭제
    @Transactional
    public Long delete(Long id) {
//        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("해당 채용공고를 찾을 수 없습니다."));
        Post post = postRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));
        postRepository.delete(post);
        return id;
    }

    // 4-1. 채용공고 목록 전체 조회
    @Transactional
    public List<PostResponseDto> findAll() {
        try {
            List<Post> list = postRepository.findAll();
            return list.stream().map(PostResponseDto::new).collect(Collectors.toList());
        } catch (Exception e) {
            // 예상치 못한 오류를 처리하기 위한 CustomException
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    // 4-2. 채용공고 검색
    @Transactional
    public List<PostResponseDto> searchByPosition(String keyword) {
        // 검색어가 없을 때
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE, "검색어를 입력하지 않았습니다.");
        }

        List<Post> list = postRepository.findByPositionContaining(keyword);
        return list.stream().map(PostResponseDto::new).collect(Collectors.toList());
    }
}
