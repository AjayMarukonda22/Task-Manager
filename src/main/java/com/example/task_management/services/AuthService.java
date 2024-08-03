package com.example.task_management.services;

import com.example.task_management.dtos.UserDto;
import com.example.task_management.exceptions.NotFoundException;
import com.example.task_management.exceptions.UserAlreadyExistException;
import com.example.task_management.exceptions.JwtTokenException;
import com.example.task_management.exceptions.JwtTokenExpiredException;
import com.example.task_management.exceptions.JwtTokenInvalidException;
import com.example.task_management.models.Session;
import com.example.task_management.models.SessionStatus;
import com.example.task_management.models.User;
import com.example.task_management.repositories.SessionRepository;
import com.example.task_management.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService implements Authentication {
    private UserRepository userRepository;
    private SessionRepository sessionRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private SecretKey secretKey;

    @Autowired
    public AuthService(UserRepository userRepository, SessionRepository sessionRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        secretKey = Jwts.SIG.HS256.key().build();
    }

    @Override
    public UserDto signUp(String userName, String password) throws UserAlreadyExistException {
        Optional<User> optionalUser = userRepository.findUserByUserName(userName);
        if (optionalUser.isPresent()) {
            throw new UserAlreadyExistException("The User with " + userName + " already Exists.");
        }
        User user1 = new User();
        user1.setUserName(userName);
        user1.setPassword(bCryptPasswordEncoder.encode(password));
        User savedUser = userRepository.save(user1);
        return UserDto.from(savedUser);
    }

    @Override
    public ResponseEntity<UserDto> logIn(String userName, String password) throws NotFoundException {
        Optional<User> optionalUser = userRepository.findUserByUserName(userName);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("The User is Not Found. Can you please SignUp");
        }
        User user = optionalUser.get();
        if (!bCryptPasswordEncoder.matches(password, user.getPassword()))
            throw new RuntimeException("The password is incorrect.");

        Map<String, Object> jwtData = new HashMap<>();
        jwtData.put("userName", userName);
        jwtData.put("created_at", java.sql.Date.valueOf(LocalDate.now()));
        jwtData.put("expiry_at", java.sql.Date.valueOf(LocalDate.now().plusDays(3)));

        String token = Jwts.builder()
                .claims(jwtData)
                .signWith(secretKey)
                .compact();

        Session session = new Session();
        session.setToken(token);
        session.setUser(user);
        LocalDate currentDate = LocalDate.now();
        session.setExpiryDate(currentDate.plusDays(3));
        session.setStatus(SessionStatus.ACTIVE);
        sessionRepository.save(session);
        UserDto userDto = UserDto.from(user);
        MultiValueMap<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add(HttpHeaders.SET_COOKIE, "auth-token: " + token);
        return new ResponseEntity<>(userDto, headers, HttpStatus.OK);
    }

    @Override
    public void logOut(String token, Long userId) throws NotFoundException {
        Optional<Session> optionalSession = sessionRepository.findSessionByTokenAndUser(token, userId);
        if (optionalSession.isEmpty())
            throw new NotFoundException("The token is invalid or the details are mismatching");
        Session session = optionalSession.get();
        session.setStatus(SessionStatus.ENDED);
        sessionRepository.save(session);
    }

    @Override
    public ResponseEntity<String> validate(String token) {
        try{
        Jws<Claims> claimsJws = Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token);

        String email = (String) claimsJws.getPayload().get("email");
        Long expiryAtInMillis = (Long) claimsJws.getPayload().get("expiry_at");
        Instant instant = Instant.ofEpochMilli(expiryAtInMillis);
        LocalDate expiryAt =  instant.atZone(ZoneId.systemDefault()).toLocalDate();
        System.out.println(expiryAt);

        if (expiryAt.isBefore(LocalDate.now())) {
            throw new JwtTokenExpiredException("Token has expired");
        }

        return new ResponseEntity<>("Token is valid", HttpStatus.OK);

    } catch (JwtException e) {
        System.err.println("JWT Exception: " + e.getMessage());
        throw new JwtTokenInvalidException("Invalid token");
    } catch (Exception e) {
        System.err.println("Unexpected Exception: " + e.getMessage());
        throw new JwtTokenException("Unexpected error");
    }
    }
}

