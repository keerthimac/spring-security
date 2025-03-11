package com.example.spring.security.with.jwt.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SignupDto {
    private String name;
    private String email;
    private String password;
    private String phone;
}
