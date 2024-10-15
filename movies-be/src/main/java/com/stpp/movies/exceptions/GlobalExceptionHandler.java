package com.stpp.movies.exceptions;

import com.stpp.movies.dto.ErrorResponseDto;
import io.swagger.v3.oas.annotations.Hidden;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<List<ErrorResponseDto>> handleValidationExceptions(MethodArgumentNotValidException ex) {
    List<ErrorResponseDto> errors = ex.getBindingResult().getFieldErrors().stream()
      .map(fieldError ->
        new ErrorResponseDto(fieldError.getDefaultMessage(), HttpStatus.BAD_REQUEST.value())
      )
      .toList();

    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NotFoundException.class)
  public ErrorResponseDto handleNotFoundException(NotFoundException ex) {
    return ErrorResponseDto.builder()
      .message(ex.getMessage())
      .status(HttpStatus.NOT_FOUND.value())
      .build();
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ErrorResponseDto> handleBadRequestException(BadRequestException ex) {
    ErrorResponseDto errorResponseDto = new ErrorResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
    return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<ErrorResponseDto> handleValidationException(ValidationException ex) {
    ErrorResponseDto errorResponseDto = new ErrorResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
    return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponseDto> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
    ErrorResponseDto errorResponseDto = new ErrorResponseDto("Invalid request body: " + ex.getMessage(), HttpStatus.BAD_REQUEST.value());
    return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
  }
}
