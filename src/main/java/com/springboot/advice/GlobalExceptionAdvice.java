package com.springboot.advice;

import com.springboot.exception.BusinessLogicException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.springboot.response.ErrorResponse;

import javax.validation.ConstraintViolationException;



@RestControllerAdvice
public class GlobalExceptionAdvice {

  @ExceptionHandler
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    final ErrorResponse response = ErrorResponse.of(e.getBindingResult());

    return response;

  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse handleConstraintViolationException(ConstraintViolationException e) {

    final ErrorResponse response = ErrorResponse.of(e.getConstraintViolations());

    return response;
  }

  @ExceptionHandler
  public ResponseEntity handleBusinessLogicException(BusinessLogicException e) {
    final ErrorResponse response = ErrorResponse.of(e.getExceptionCode());

    return new ResponseEntity<>(response, HttpStatus.valueOf(e.getExceptionCode()
        .getStatus()));
  }
}
