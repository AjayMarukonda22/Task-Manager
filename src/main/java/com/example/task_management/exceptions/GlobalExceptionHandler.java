package com.example.task_management.exceptions;

import com.example.task_management.dtos.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionDto> handleNotFoundException(NotFoundException notFoundException) {
        return new ResponseEntity<>(new ExceptionDto(HttpStatus.NOT_FOUND, notFoundException.getMessage()), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ExceptionDto> handleUserAlreadyExistException(UserAlreadyExistException userAlreadyExistException) {
        return new ResponseEntity<>(new ExceptionDto(HttpStatus.ALREADY_REPORTED, userAlreadyExistException.getMessage()), HttpStatus.ALREADY_REPORTED);
    }

    @ExceptionHandler(JwtTokenExpiredException.class)
    public ResponseEntity<?> handleJwtTokenExpiredException(JwtTokenExpiredException jwtTokenExpiredException) {

        return new ResponseEntity<>(jwtTokenExpiredException.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(JwtTokenInvalidException.class)
    public ResponseEntity<?> handleJwtTokenInvalidException(JwtTokenInvalidException jwtTokenInvalidException) {

        return new ResponseEntity<>(jwtTokenInvalidException.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(JwtTokenException.class)
    public ResponseEntity<?> handleJwtTokenException(JwtTokenException jwtTokenException) {

        return new ResponseEntity<>(jwtTokenException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
