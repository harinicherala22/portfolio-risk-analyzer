package com.harini.portfolio_risk_analyzer.controller;

import com.harini.portfolio_risk_analyzer.dto.PortfolioRequest;
import com.harini.portfolio_risk_analyzer.entity.Portfolio;
import com.harini.portfolio_risk_analyzer.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/portfolio")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService service;

    @PostMapping("/add")
    public String add(
            @RequestBody PortfolioRequest request,
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);

        return service.add(request, token);
    }

    @GetMapping("/all")
    public List<Portfolio> getAll(
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);

        return service.getAll(token);
    }
    @GetMapping("/summary")
    public Double summary(
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7);

        return service.getTotal(token);
    }
    @DeleteMapping("/delete/{id}")
    public String delete(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.replace("Bearer ", "");

        return service.delete(id, token);
    }
    @GetMapping("/risk")
    public String risk(
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.replace("Bearer ", "");

        return service.riskScore(token);
    }
}