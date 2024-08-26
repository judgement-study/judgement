package com.inu.algomaster.config.security;

import com.inu.algomaster.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        log.info("[loadUserByUsername] loadUserByUsername 수행. username: {}", userId);
        return (UserDetails) userRepository.findByUserId(userId).orElseThrow(()->new NoSuchElementException("유저가 존재하지 않습니다."));
    }
}