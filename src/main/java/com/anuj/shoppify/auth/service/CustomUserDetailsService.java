package com.anuj.shoppify.auth.service;

import com.anuj.shoppify.auth.dto.LoggedInUser;
import com.anuj.shoppify.auth.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public LoggedInUser loadUserByUsername(String email) throws UsernameNotFoundException {
        com.anuj.shoppify.auth.entity.User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new LoggedInUser(user.getId(), user.getEmail(), user.getPassword(), Collections.emptyList());
    }
}
