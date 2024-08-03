package com.example.task_management.services;

import com.example.task_management.dtos.UserDto;
import com.example.task_management.exceptions.NotFoundException;
import com.example.task_management.exceptions.UserAlreadyExistException;
import org.springframework.http.ResponseEntity;

public interface Authentication {

    UserDto signUp(String userName, String password) throws UserAlreadyExistException;
    ResponseEntity<UserDto> logIn(String userName, String password) throws NotFoundException;
    void logOut(String token, Long userId) throws NotFoundException;
    ResponseEntity<String> validate(String token);
}
