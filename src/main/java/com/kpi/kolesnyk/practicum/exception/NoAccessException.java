package com.kpi.kolesnyk.practicum.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NoAccessException extends RuntimeException {
    private final String message;
}
