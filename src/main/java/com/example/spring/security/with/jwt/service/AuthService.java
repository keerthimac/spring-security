package com.example.spring.security.with.jwt.service;

import com.example.spring.security.with.jwt.dto.SignupDto;
import com.example.spring.security.with.jwt.dto.UserDto;


public interface AuthService {
    UserDto createUser(SignupDto signupDto);
    String verify(SignupDto signupDto);
}
