package com.harini.portfolio_risk_analyzer.repository;

import com.harini.portfolio_risk_analyzer.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    List<Portfolio> findByUserEmail(String userEmail);
}