package com.example.spring.security.with.jwt.service;

import com.example.spring.security.with.jwt.dto.SignupDto;
import com.example.spring.security.with.jwt.dto.UserDto;
import com.example.spring.security.with.jwt.entity.UserEntity;
import com.example.spring.security.with.jwt.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    final UserRepository userRepository;
    final AuthenticationManager authManager;
    final PasswordEncoder passwordEncoder;
    final JwtService jwtService;

    @Override
    public UserDto createUser(SignupDto signupUserDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(signupUserDto.getName());
        userEntity.setEmail(signupUserDto.getEmail());
        userEntity.setPassword(passwordEncoder.encode(signupUserDto.getPassword()));
        userEntity.setPhone(signupUserDto.getPhone());
        UserEntity savedUser = userRepository.save(userEntity);
        UserDto userDto = new UserDto();
        userDto.setName(savedUser.getName());
        userDto.setEmail(savedUser.getEmail());
        userDto.setPhone(savedUser.getPhone());
        return userDto;
    }

    @Override
    public String verify(SignupDto signupDto) {
        Authentication authentication = authManager.
                authenticate(new UsernamePasswordAuthenticationToken(signupDto.getName(), signupDto.getPassword()));

        if(authentication.isAuthenticated()){
            return jwtService.generateToken(signupDto.getName());
        }
        return "Fail";
    }
}
