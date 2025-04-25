package com.anuj.shoppify.auth.service;
import com.anuj.shoppify.auth.dto.AuthResponse;
import com.anuj.shoppify.auth.entity.User;
import com.anuj.shoppify.auth.repository.UserRepository;
import com.anuj.shoppify.auth.dto.UserDTO;
import com.anuj.shoppify.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

    public void createUser(UserDTO userDTO) {
        User user = User.builder()
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .build();
        userRepository.save(user);
        log.info("SignIn Successful,{} ", user);
    }

    public void login(UserDTO userDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword())
            );

            String token = jwtUtil.generateToken(userDTO.getEmail());
            log.info("login Successful!");
        } catch (Exception e) {
            log.error("Invalid email or password, please enter correct info");
        }
    }
}
