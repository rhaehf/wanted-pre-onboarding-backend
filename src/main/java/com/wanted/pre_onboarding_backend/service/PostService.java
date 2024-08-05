package com.wanted.pre_onboarding_backend.service;

import com.wanted.pre_onboarding_backend.domain.Company;
import com.wanted.pre_onboarding_backend.domain.Post;
import com.wanted.pre_onboarding_backend.dto.PostRequestDto;
import com.wanted.pre_onboarding_backend.dto.PostResponseDto;
import com.wanted.pre_onboarding_backend.repository.CompanyRepository;
import com.wanted.pre_onboarding_backend.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
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

        if (companyId != null) {
            Company company = companyRepository.findById(companyId).orElseThrow(() -> new EntityNotFoundException("해당 companyId로 회사를 찾을 수 없습니다."));
            Post post = postRepository.save(postRequestDto.toEntity(company));
            return post.getPostId();
        } else {
            throw new IllegalStateException("입력한 데이터가 누락되어 채용공고를 등록할 수 없습니다.");
        }
    }

    // 2. 채용공고 수정
    @Transactional
    public Long update(Long postId, PostRequestDto dto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("해당 채용공고를 찾을 수 없습니다."));
        post.update(dto.getPosition(), dto.getSigningBonus(), dto.getContent(), dto.getSkill());
        return postId;
    }

    // 3. 채용공고 삭제
    @Transactional
    public void delete(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("해당 채용공고를 찾을 수 없습니다."));
        postRepository.delete(post);
    }

    // 4-1. 채용공고 목록 전체 조회
    @Transactional
    public List<PostResponseDto> findAll() {
        List<Post> list = postRepository.findAll();
        return list.stream().map(PostResponseDto::new).collect(Collectors.toList());
    }

    // 4-2. 채용공고 검색
    @Transactional
    public List<PostResponseDto> searchByPosition(String keyword) {
        List<Post> list = postRepository.findByPositionContaining(keyword);
        return list.stream().map(PostResponseDto::new).collect(Collectors.toList());
    }
}
