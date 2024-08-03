package com.example.task_management.exceptions;

public class UserAlreadyExistException extends Exception{

    private String message;
    public UserAlreadyExistException(String message) {
        super(message);
    }


}
