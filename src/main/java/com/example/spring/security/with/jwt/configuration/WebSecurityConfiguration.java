package com.example.spring.security.with.jwt.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration // This tells Spring that this class is a configuration we are creating beans in this class
@EnableWebSecurity //This disables the autoconfiguration of spring security
public class WebSecurityConfiguration {

    //@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean //The Chane of security filter will be added to the filter chain
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(customizer ->customizer.disable());
        //disable csrf (Login token for Post,put,delete actions). now any of the methods can be called.
        http.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());
        //enable authentication. so required to be authenticated. but cannot call eny requests because login from or http requests are disabled now
        http.formLogin(Customizer.withDefaults());
        //enable form login but net yet enables http requests like postmen requests
        http.httpBasic(Customizer.withDefaults());
        //enables http basic authentication
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        //This methods weather session should be created or not. now session is not created. every time new session is created
        // http.formLogin(Customizer.withDefaults()); should disabled because its redirecting to login page

//        http
//                .csrf(customizer ->customizer.disable())
//                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
//                .formLogin(Customizer.withDefaults())
//                .httpBasic(Customizer.withDefaults())
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .build();
        //We can write this in one line as well

        return http.build();
    }
}
