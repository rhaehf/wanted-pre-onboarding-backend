package com.wanted.pre_onboarding_backend.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // Custom Exception
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(final CustomException e) {
        log.error("handleCustomException throw CustomException: {}", e.getErrorCode(), e);
        return ResponseEntity
                .status(e.getErrorCode().getStatus().value())
                .body(new ErrorResponse(e.getErrorCode(), e.getCustomMessage()));
    }

    // 유효성 검증(@Valid) 관련 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException throw MethodArgumentNotValidException: {}", e.getMessage(), e);
        String errorMessage = e.getBindingResult().getFieldError().getDefaultMessage();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ErrorCode.INVALID_INPUT_VALUE, errorMessage));
    }

    // HTTP 요청 본문을 읽거나 변환하는 과정에서 문제가 발생했을 때
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("handleHttpMessageNotReadableException throw HttpMessageNotReadableException: {}", e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ErrorCode.INVALID_INPUT_VALUE));
    }

    // 요청 경로에 누락된 파라미터가 있을 때
    @ExceptionHandler(MissingPathVariableException.class)
    protected ResponseEntity<ErrorResponse> handleMissingPathVariableException(MissingPathVariableException e) {
        log.error("handleMissingPathVariableException throw MissingPathVariableException: {}", e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ErrorCode.MISSING_PATH_VARIABLE));
    }

    // JPA 관련 예외 처리
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleEntityNotFoundException(final EntityNotFoundException e) {
        log.error("handleEntityNotFoundException throw EntityNotFoundException: {}", e.getMessage(), e);
        CustomException customException = new CustomException(ErrorCode.ENTITY_NOT_FOUND, e.getMessage());
        return ResponseEntity
                .status(customException.getErrorCode().getStatus().value())
                .body(new ErrorResponse(customException.getErrorCode(), customException.getCustomMessage()));
    }

    // hibernate 관련 에러 처리
    @ExceptionHandler(value = {ConstraintViolationException.class, DataIntegrityViolationException.class})
    protected ResponseEntity<ErrorResponse> handleDataException(final Exception e) {
        log.error("handleDataException throw Exception : {}", e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.CONFLICT) // 데이터 충돌 오류
                .body(new ErrorResponse(ErrorCode.DUPLICATE_RESOURCE));
    }
}
