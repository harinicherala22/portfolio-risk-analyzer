package com.harini.portfolio_risk_analyzer.service;

import com.harini.portfolio_risk_analyzer.dto.LoginRequest;
import com.harini.portfolio_risk_analyzer.dto.RegisterRequest;
import com.harini.portfolio_risk_analyzer.entity.User;
import com.harini.portfolio_risk_analyzer.repository.UserRepository;
import com.harini.portfolio_risk_analyzer.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repo;
    private final BCryptPasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public String register(RegisterRequest request) {

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRole("USER");

        repo.save(user);

        return "User Registered Successfully";
    }
    public String login(LoginRequest request) {

        User user = repo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (encoder.matches(request.getPassword(), user.getPassword())) {
            return jwtUtil.generateToken(user.getEmail());
        }

        throw new RuntimeException("Invalid Password");
    }
}