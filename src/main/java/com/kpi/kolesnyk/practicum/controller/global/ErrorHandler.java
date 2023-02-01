package com.kpi.kolesnyk.practicum.controller.global;

import com.kpi.kolesnyk.practicum.exception.NoAccessException;
import com.kpi.kolesnyk.practicum.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.python.core.PyException;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class ErrorHandler {
    private static final String SERVER_ERROR = "Server error";
    private static final String DEFAULT_ERROR_PAGE = "error";
    private static final String TIMEOUT_MESSAGE = "Such a long execution time, we not allow code lasts more than 12 seconds. Consider refactoring your solution";

    @ExceptionHandler(ResourceNotFoundException.class)
    public String resourceNotFound(ResourceNotFoundException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return DEFAULT_ERROR_PAGE;
    }

    @ExceptionHandler(AsyncRequestTimeoutException.class)
    public String requestTimeout(Model model) {
        model.addAttribute("message", TIMEOUT_MESSAGE);
        return DEFAULT_ERROR_PAGE;
    }

    @ExceptionHandler(PyException.class)
    public String pythonException(PyException e, Model model) {
        model.addAttribute("message", e.traceback != null
                ? e.traceback.dumpStack() + e.getMessage()
                : e.getMessage());
        return DEFAULT_ERROR_PAGE;
    }

    @ExceptionHandler(NoAccessException.class)
    public String noAccess(NoAccessException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return DEFAULT_ERROR_PAGE;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String methodArgumentsNotValid(MethodArgumentNotValidException e, Model model) {
        model.addAttribute("message", e.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.joining("; ")));
        return DEFAULT_ERROR_PAGE;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public String constraintViolation(ConstraintViolationException e, Model model) {
        model.addAttribute("message", e.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.joining("; ")));
        return DEFAULT_ERROR_PAGE;
    }

    @ExceptionHandler(Exception.class)
    public String exception(Exception e, Model model) {
        log.error(e.getMessage(), e);
        model.addAttribute("message", SERVER_ERROR);
        return DEFAULT_ERROR_PAGE;
    }
}
