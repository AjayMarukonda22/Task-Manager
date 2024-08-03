package com.example.task_management.exceptions;


public class JwtTokenInvalidException extends JwtTokenException {
    public JwtTokenInvalidException(String message) {
        super(message);
    }
}