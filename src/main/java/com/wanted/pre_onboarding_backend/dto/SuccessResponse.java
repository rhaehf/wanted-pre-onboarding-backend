package com.wanted.pre_onboarding_backend.dto;

import lombok.Getter;

@Getter
public class SuccessResponse<T> {

    private final boolean success = true;
    private final T response;

    public SuccessResponse(T response) {
        this.response = response;
    }
}