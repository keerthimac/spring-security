package com.example.spring.security.with.jwt.configuration;
import com.example.spring.security.with.jwt.filters.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration // This tells Spring that this class is a configuration we are creating beans in this class
@EnableWebSecurity //This disables the autoconfiguration of spring security
@RequiredArgsConstructor
public class WebSecurityConfiguration {

   final UserDetailsService userDetailsService;
   final JwtFilter jwtFilter;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean //The Chane of security filter will be added to the filter chain
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(customizer ->customizer.disable());
        //disable csrf (Login token for Post,put,delete actions). now any of the methods can be called.
        http.authorizeHttpRequests(authorize -> authorize.requestMatchers("/login","/signup").permitAll().anyRequest().authenticated());
        //enable authentication. so required to be authenticated. but cannot call eny requests because login from or http requests are disabled now
        //http.formLogin(Customizer.withDefaults());
        //enable form login but net yet enables http requests like postmen requests
        http.httpBasic(Customizer.withDefaults());
        //enables http basic authentication
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        //This methods weather session should be created or not. now session is not created. every time new session is created
        // http.formLogin(Customizer.withDefaults()); should disabled because its redirecting to login page
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        //We can write this in one line as well

//        http
//                .csrf(customizer ->customizer.disable())
//                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
//                .formLogin(Customizer.withDefaults())
//                .httpBasic(Customizer.withDefaults())
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .build();


        return http.build();
    }


    //Inserted of using spring`s built-in authentication we are using custom authentication for our apps. this is the general practice
    //for custom authentication

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(); // method of authentication works with database
        provider.setPasswordEncoder(passwordEncoder()); // can be used custom one, Either NoOpPasswordEncoder or BCrypt. best practice is BCrypt
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    // In spring security, AuthenticationManager is is responsible for authenticating users. but with default configuration this was managed by spring security
    //when we use custom authentication, we need to create our own AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }



    //This is Also Default Security Configuration. in the production this method not used. but may have some use cases.
    //

//    @Bean
//    public UserDetailsService userDetailsService() {
//
//        UserDetails user1 = User
//                .withDefaultPasswordEncoder()
//                .username("user")
//                .password("password")
//                .roles("USER")
//                .build();
//
//        UserDetails user2 = User
//                .withDefaultPasswordEncoder()
//                .username("admin")
//                .password("password")
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(user1,user2);
//
//    }


}
