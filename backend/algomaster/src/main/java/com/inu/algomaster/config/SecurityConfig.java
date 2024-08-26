package com.inu.algomaster.config;

import com.inu.algomaster.config.security.JwtAuthFilter;
import com.inu.algomaster.config.security.JwtProvider;
import com.inu.algomaster.config.security.exception.CustomAccessDeniedHandler;
import com.inu.algomaster.config.security.exception.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final JwtProvider jwtProvider;

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        //UI를 사용하는 것을 기본값으로 가진 시큐리티 설정을 비활성화
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(requests ->
                        requests
                                .requestMatchers("/swagger-resources/**", "/swagger-ui/index.html", "/webjars/**", "/swagger/**", "/users/exception", "/v3/api-docs/**", "/swagger-ui/**").permitAll()
                                .requestMatchers("/register/sign-in", "/users/sign-up").permitAll()
                                //.requestMatchers(HttpMethod.GET,"/users").permitAll()
                                .requestMatchers("**exception**").permitAll()
                                .anyRequest().hasRole("USER")
                )
                .addFilterBefore(new JwtAuthFilter(jwtProvider),
                        UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .accessDeniedHandler(new CustomAccessDeniedHandler())
                                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                );
        return httpSecurity.build();
    }

}