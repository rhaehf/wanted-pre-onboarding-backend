package com.wanted.pre_onboarding_backend.controller;

import com.wanted.pre_onboarding_backend.dto.PostRequestDto;
import com.wanted.pre_onboarding_backend.dto.PostResponseDto;
import com.wanted.pre_onboarding_backend.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    // 1. 채용공고 등록
    @PostMapping("/posts")
    // @Valid를 통해서 companyId가 @NotNull인지 검사함
    public ResponseEntity savePosts(@RequestBody @Valid PostRequestDto postRequestDto) {
        Long postId = postService.save(postRequestDto);

        // body에 등록한 채용공고의 id를 담아 반환함
        Map<String, Long> response = new HashMap<>();
        response.put("postId", postId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 2. 채용공고 수정
    @PatchMapping("/posts/{id}")
    public ResponseEntity updatePosts(@PathVariable(name = "id") Long postId, @RequestBody PostRequestDto dto) {
        // body에 등록한 채용공고의 id를 담아 반환함
        return ResponseEntity.status(HttpStatus.OK).body(postService.update(postId, dto));
    }

    // 3. 채용공고 삭제
    @DeleteMapping("/posts/{id}")
    public ResponseEntity deletePosts(@PathVariable(name = "id") Long postId) {
        postService.delete(postId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 4-1. 채용공고 목록 전체 조회
    @GetMapping("/posts")
    public ResponseEntity<List<PostResponseDto>> findAllPosts() {
        return ResponseEntity.status(HttpStatus.OK).body(postService.findAll());
    }

    // 4-2. 채용공고 검색 (채용포지션으로)
    @GetMapping("/posts/search")
    public ResponseEntity<List<PostResponseDto>> searchPosts(@RequestParam(name = "position") String keyword) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.searchByPosition(keyword));
    }
}