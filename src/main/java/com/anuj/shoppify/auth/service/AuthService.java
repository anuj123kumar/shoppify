package com.anuj.shoppify.auth.service;
import com.anuj.shoppify.auth.dto.AuthResponse;
import com.anuj.shoppify.auth.entity.User;
import com.anuj.shoppify.auth.repository.UserRepository;
import com.anuj.shoppify.auth.dto.UserDTO;
import com.anuj.shoppify.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public AuthResponse createUser(UserDTO userDTO) {
        User user = User.builder()
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .build();
        userRepository.save(user);
        return new AuthResponse(true,"SignIn Successful",null);
    }

    public AuthResponse login(UserDTO userDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword())
            );

            String token = jwtUtil.generateToken(userDTO.getEmail());
            return new AuthResponse(true, "Login successful", token);
        } catch (Exception e) {
            return new AuthResponse(false, "Invalid email or password", null);
        }
    }
}
