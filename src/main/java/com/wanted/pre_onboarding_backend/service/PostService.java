package com.wanted.pre_onboarding_backend.service;

import com.wanted.pre_onboarding_backend.domain.Company;
import com.wanted.pre_onboarding_backend.domain.Post;
import com.wanted.pre_onboarding_backend.dto.PostRequestDto;
import com.wanted.pre_onboarding_backend.dto.PostResponseDto;
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

    // 1. 채용공고 등록
    @Transactional
    public Long save(PostRequestDto postRequestDto) {
        Long companyId = postRequestDto.getCompanyId();
        Company company = new Company(companyId);

        Post post = postRepository.save(postRequestDto.toEntity(company));
        return post.getPostId();
    }

    // 2. 채용공고 수정
    @Transactional
    public Long update(Long postId, PostRequestDto dto) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("해당 채용공고를 찾을 수 없습니다."));
        post.update(dto.getPosition(), dto.getSigningBonus(), dto.getContent(), dto.getSkill());
        return postId;
    }

    // 4. 채용공고 목록 전체 조회
    @Transactional
    public List<PostResponseDto> findAll() {
        List<Post> list = postRepository.findAll();
        return list.stream().map(PostResponseDto::new).collect(Collectors.toList());
    }
}
