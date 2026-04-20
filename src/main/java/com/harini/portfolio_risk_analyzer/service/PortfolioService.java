package com.harini.portfolio_risk_analyzer.service;

import com.harini.portfolio_risk_analyzer.dto.PortfolioRequest;
import com.harini.portfolio_risk_analyzer.entity.Portfolio;
import com.harini.portfolio_risk_analyzer.repository.PortfolioRepository;
import com.harini.portfolio_risk_analyzer.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final PortfolioRepository repo;
    private final JwtUtil jwtUtil;

    public String add(PortfolioRequest request, String token) {

        String email = jwtUtil.extractEmail(token);

        Portfolio p = new Portfolio();
        p.setAssetName(request.getAssetName());
        p.setQuantity(request.getQuantity());
        p.setBuyPrice(request.getBuyPrice());
        p.setUserEmail(email);

        repo.save(p);

        return "Asset Added";
    }

    public List<Portfolio> getAll(String token) {

        String email = jwtUtil.extractEmail(token);

        return repo.findByUserEmail(email);
    }

    public Double getTotal(String token) {

        String email = jwtUtil.extractEmail(token);

        List<Portfolio> list = repo.findByUserEmail(email);

        return list.stream()
                .mapToDouble(p -> p.getQuantity() * p.getBuyPrice())
                .sum();
    }
    public String delete(Long id, String token) {

        String email = jwtUtil.extractEmail(token);

        Portfolio asset = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Asset not found"));

        if (!asset.getUserEmail().equals(email)) {
            throw new RuntimeException("Unauthorized");
        }

        repo.delete(asset);

        return "Asset Deleted";
    }
    public String riskScore(String token) {

        String email = jwtUtil.extractEmail(token);

        List<Portfolio> list = repo.findByUserEmail(email);

        double total = list.stream()
                .mapToDouble(p -> p.getQuantity() * p.getBuyPrice())
                .sum();

        if (total < 50000) {
            return "LOW RISK";
        } else if (total < 200000) {
            return "MEDIUM RISK";
        } else {
            return "HIGH RISK";
        }
    }

}