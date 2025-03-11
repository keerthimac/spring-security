package com.example.spring.security.with.jwt.controller;

import com.example.spring.security.with.jwt.dto.SignupDto;
import com.example.spring.security.with.jwt.dto.UserDto;
import com.example.spring.security.with.jwt.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class SignupUserController {

    final AuthService authService;

    @GetMapping("/")
    public String getSessionId(HttpServletRequest request){
        return request.getSession().getId();
    }

    @GetMapping("/csrf-token")
    public CsrfToken getSessionToken(HttpServletRequest request){
        return (CsrfToken) request.getAttribute("_csrf");
    }

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody SignupDto signupDto){
        UserDto userDto = authService.createUser(signupDto);
        if(userDto != null){
            return new ResponseEntity<>(userDto, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Signup Failed",HttpStatus.BAD_REQUEST);
    }
}
