package com.example.task_management.controllers;

import com.example.task_management.dtos.*;
import com.example.task_management.exceptions.NotFoundException;
import com.example.task_management.exceptions.UserAlreadyExistException;
import com.example.task_management.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;
    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody RegisterRequestDto registerRequestDto) throws UserAlreadyExistException {
          UserDto userDto = authService.signUp(registerRequestDto.getUserName(), registerRequestDto.getPassword());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
    @PostMapping("/logIn")
    public ResponseEntity<UserDto> logIn(@RequestBody LogInRequestDto logInRequestDto) throws NotFoundException {
        return authService.logIn(logInRequestDto.getUserName(), logInRequestDto.getPassword());

    }
    @PostMapping("/logOut")
    public void logOut(@RequestBody LogOutRequestDto logOutRequestDto) throws NotFoundException{
             authService.logOut(logOutRequestDto.getToken(), logOutRequestDto.getUserId());
    }
    @PostMapping("/validate")
    public ResponseEntity<String> validate(@RequestBody String token) {
        return authService.validate(token);
    }
}
