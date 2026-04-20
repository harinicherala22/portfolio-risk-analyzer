package com.harini.portfolio_risk_analyzer.repository;

import com.harini.portfolio_risk_analyzer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}