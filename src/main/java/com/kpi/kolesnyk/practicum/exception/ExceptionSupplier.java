package com.kpi.kolesnyk.practicum.exception;

import lombok.experimental.UtilityClass;

import java.util.function.Supplier;

@UtilityClass
public class ExceptionSupplier {
    public static final Supplier<ResourceNotFoundException> RESOURCE_NOT_FOUND =
            () -> new ResourceNotFoundException("Resource not found");

    public static final Supplier<ResourceNotFoundException> TASK_NOT_FOUND =
            () -> new ResourceNotFoundException("Task not found");

    public static final Supplier<ResourceNotFoundException> USER_NOT_FOUND =
            () -> new ResourceNotFoundException("User not found");

    public static final Supplier<NoAccessException> NO_ACCESS =
            () -> new NoAccessException("You have no access");

    public static final Supplier<NoAccessException> USER_ALREADY_REGISTERED =
            () -> new NoAccessException("User already registered");
}
