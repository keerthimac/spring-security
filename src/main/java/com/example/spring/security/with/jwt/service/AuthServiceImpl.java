package com.example.spring.security.with.jwt.service;

import com.example.spring.security.with.jwt.dto.SignupDto;
import com.example.spring.security.with.jwt.dto.UserDto;
import com.example.spring.security.with.jwt.entity.UserEntity;
import com.example.spring.security.with.jwt.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    final UserRepository userRepository;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
    //final PasswordEncoder passwordEncoder;

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
}
