package com.harini.portfolio_risk_analyzer.controller;

import com.harini.portfolio_risk_analyzer.dto.LoginRequest;
import com.harini.portfolio_risk_analyzer.dto.RegisterRequest;
import com.harini.portfolio_risk_analyzer.dto.PortfolioRequest;
import com.harini.portfolio_risk_analyzer.entity.Portfolio;
import com.harini.portfolio_risk_analyzer.service.AuthService;
import com.harini.portfolio_risk_analyzer.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;


    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        return service.register(request);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return service.login(request);
    }

}