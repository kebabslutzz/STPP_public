package com.stpp.movies.exceptions;

import com.stpp.movies.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorResponseDto>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<ErrorResponseDto> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> {
                    ErrorResponseDto errorResponseDto = new ErrorResponseDto();
                    errorResponseDto.setMessage(fieldError.getDefaultMessage());
                    errorResponseDto.setStatus(HttpStatus.BAD_REQUEST.value());
                    return errorResponseDto;
                })
                .collect(Collectors.toList());

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
