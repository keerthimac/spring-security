package com.example.spring.security.with.jwt.service;

import com.example.spring.security.with.jwt.entity.UserEntity;
import com.example.spring.security.with.jwt.entity.UserPrincipal;
import com.example.spring.security.with.jwt.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailServiceImpl implements CustomUserDetailsService {

    final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity byName = userRepository.findByName(username);
        if(byName == null) {
            throw new UsernameNotFoundException("User not found");
        } else {
            return new UserPrincipal(byName);
        }
    }
}
