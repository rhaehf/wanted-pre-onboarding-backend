package com.wanted.pre_onboarding_backend.controller;

import com.wanted.pre_onboarding_backend.dto.PostRequestDto;
import com.wanted.pre_onboarding_backend.dto.PostResponseDto;
import com.wanted.pre_onboarding_backend.dto.SuccessResponse;
import com.wanted.pre_onboarding_backend.exception.CustomException;
import com.wanted.pre_onboarding_backend.exception.ErrorCode;
import com.wanted.pre_onboarding_backend.exception.ErrorResponse;
import com.wanted.pre_onboarding_backend.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
        return ResponseEntity.ok(new SuccessResponse<>(response));
    }

    // 2. 채용공고 수정
    @PatchMapping("/posts/{id}")
    // id를 입력하지 않는다고해서 null로 설정되지 않고, 에러가 발생함
    public ResponseEntity updatePosts(@PathVariable(name = "id") Long id, @RequestBody PostRequestDto dto) {
        Long postId = postService.update(id, dto);

        // body에 수정한 채용공고의 id를 담아 반환함
        Map<String, Long> response = new HashMap<>();
        response.put("postId", postId);
        return ResponseEntity.ok(new SuccessResponse<>(response));
    }

    // 3. 채용공고 삭제
    @DeleteMapping("/posts/{id}")
    public ResponseEntity deletePosts(@PathVariable(name = "id") Long id) {
        Long postId = postService.delete(id);
        Map<String, Long> response = new HashMap<>();
        response.put("postId", postId);
        return ResponseEntity.ok(new SuccessResponse<>(response));
    }

    // 4-1. 채용공고 목록 전체 조회
    @GetMapping("/posts")
    public ResponseEntity findAllPosts() {
        List<PostResponseDto> findAllList = postService.findAll();

        // DB에 등록된 채용공고가 없는 경우 NO_CONTENT(204) 응답 반환
        if (findAllList.isEmpty()) {
            // 204 No Content 응답은 보통 바디가 없을 때 사용됨
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(new SuccessResponse<>(findAllList));
    }

    // 4-2. 채용공고 검색 (채용포지션으로)
    @GetMapping("/posts/search")
    public ResponseEntity searchPosts(@RequestParam(name = "position") String keyword) {
        try {
            List<PostResponseDto> searchList = postService.searchByPosition(keyword);

            // 검색 결과가 없는 경우 NO_CONTENT(204) 응답 반환
            if (searchList.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            // 정상적인 경우
            Map<String, Object> response = new HashMap<>();
            response.put("count", searchList.size());
            response.put("data", searchList);
            return ResponseEntity.ok(new SuccessResponse<>(response));
        } catch (CustomException e) {
            // 비즈니스 로직이나 사용자 입력에 대한 오류를 처리하기 위해서
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(e.getErrorCode(), e.getMessage()));
        } catch (Exception e) {
            // 예기치 못한 시스템 오류나 런타임 예외를 처리하기 위해서 (데이터베이스 연결 실패, null 포인터 예외 등)
            return ResponseEntity.internalServerError()
                    .body(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR));
        }
    }

    // 5. 채용공고 상세 페이지 확인
    @GetMapping("/posts/detail")
    public ResponseEntity detailPosts(@RequestParam(name = "postId") Long postId) {
        try {
            // postId가 null이거나 유효하지 않은 경우를 체크
            if (postId == null || postId <= 0) {
                throw new CustomException(ErrorCode.INVALID_INPUT_VALUE, "유효하지 않은 채용공고 ID입니다.");
            }
            // 정상적인 경우
            PostResponseDto post = postService.findDetail(postId);
            return ResponseEntity.ok(new SuccessResponse<>(post));

        } catch (CustomException e) {
            // 비즈니스 로직에서 발생하는 예외를 처리
            return ResponseEntity.status(e.getErrorCode().getStatus())
                    .body(new ErrorResponse(e.getErrorCode(), e.getMessage()));

        } catch (Exception e) {
            // 예기치 못한 예외를 처리
            return ResponseEntity.internalServerError()
                    .body(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR));
        }
    }
}