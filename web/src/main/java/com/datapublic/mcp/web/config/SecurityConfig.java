package com.datapublic.mcp.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security 설정
 * 모든 요청을 허용하도록 설정
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .anyRequest().permitAll()  // 모든 요청 허용
            )
            .csrf(csrf -> csrf.disable())  // CSRF 비활성화
            .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));  // H2 콘솔을 위한 설정
        
        return http.build();
    }
}


