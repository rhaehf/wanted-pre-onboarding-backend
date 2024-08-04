package com.wanted.pre_onboarding_backend.controller;

import com.wanted.pre_onboarding_backend.dto.PostRequestDto;
import com.wanted.pre_onboarding_backend.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    // 1. 채용공고 등록
    @PostMapping("/posts")
    public ResponseEntity savePosts(@RequestBody @Valid PostRequestDto postRequestDto) {
        // body에 담는 데이터는 등록한 채용공고의 id이다.
        return ResponseEntity.status(HttpStatus.OK).body(postService.save(postRequestDto));
    }

    // 2. 채용공고 수정
    @PatchMapping("/posts/{id}")
    public ResponseEntity updatePosts(@PathVariable(name = "id") Long postId, @RequestBody PostRequestDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.update(postId, dto));
    }
}
