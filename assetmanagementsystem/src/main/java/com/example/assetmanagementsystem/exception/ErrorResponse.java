package com.example.assetmanagementsystem.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private final String error;

    public ErrorResponse(String errorMessage) {
        this.error = errorMessage;
    }
}

