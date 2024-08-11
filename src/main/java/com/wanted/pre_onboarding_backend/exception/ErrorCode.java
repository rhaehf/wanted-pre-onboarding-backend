package com.wanted.pre_onboarding_backend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    // 400 BAD_REQUEST : 잘못된 요청
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    // @PathVariable, @RequestParam 가 잘못되었을 때
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "클라이언트의 입력 값을 확인해주세요."),
    // @RequestBody의 입력 값이 유효하지 않을 때
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "파라미터 값을 확인해주세요."),
    MISSING_PATH_VARIABLE(HttpStatus.BAD_REQUEST, "요청 경로에 누락된 파라미터가 있습니다."),
    METHOD_ARGUMENT_TYPE_MISMATCH(HttpStatus.BAD_REQUEST, "요청 경로에 입력한 파라미터 형식이 올바르지 않습니다."),

    // 401 UNAUTHORIZED : 인증되지 않은 사용자

    // 403 FORBIDDEN : 인증되지 않은 상태에서 인증에 필요한 리소스 접근

    // 404 NOT_FOUND : Resource 를 찾을 수 없음
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "JPA 관련 - 엔티티를 찾을 수 없습니다."),
    COMPANY_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 companyId로 회사를 찾을 수 없습니다."),
    POSTS_NOT_FOUND(HttpStatus.NOT_FOUND, "채용공고를 찾을 수 없습니다."),

    // 405 METHOD_NOT_ALLOWED: 허용되지 않은 Request Method 호출
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 메서드입니다."),

    // 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재
    DUPLICATE_RESOURCE(HttpStatus.CONFLICT, "데이터가 이미 존재합니다"),

    // 500 INTERNAL SERVER ERROR : 서버가 처리 방법을 모르는 상황 발생
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류입니다."),
    ;

    private final HttpStatus status;
    private final String message;
}
