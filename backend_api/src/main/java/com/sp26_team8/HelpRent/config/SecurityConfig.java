package com.sp26_team8.HelpRent.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.sp26_team8.HelpRent.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }
 

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .userDetailsService(userService)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/ui/login", "/ui/signup", "/css/**", "/js/**").permitAll()
                .requestMatchers("/api/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/ui/login")
                .failureUrl("/ui/login?error")
                .defaultSuccessUrl("/ui/dashboard", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/ui/logout")
                .logoutSuccessUrl("/ui/login")
            );

        return http.build();
    }
    
}