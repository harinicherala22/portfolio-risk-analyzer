package com.harini.portfolio_risk_analyzer.service;

import com.harini.portfolio_risk_analyzer.entity.Portfolio;
import com.harini.portfolio_risk_analyzer.repository.PortfolioRepository;
import com.harini.portfolio_risk_analyzer.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PortfolioServiceTest {

    @Mock
    PortfolioRepository repo;

    @Mock
    JwtUtil jwtUtil;

    @InjectMocks
    PortfolioService service;

    private Portfolio asset(int qty, double price, String email) {
        Portfolio p = new Portfolio();
        p.setQuantity(qty);
        p.setBuyPrice(price);
        p.setUserEmail(email);
        return p;
    }

    @Test
    void testLowRisk() {
        when(jwtUtil.extractEmail("token")).thenReturn("harini@gmail.com");
        when(repo.findByUserEmail("harini@gmail.com"))
                .thenReturn(List.of(asset(10, 3000, "harini@gmail.com"))); // 30000

        String result = service.riskScore("token");

        assertEquals("LOW RISK", result);
    }

    @Test
    void testMediumRisk() {
        when(jwtUtil.extractEmail("token")).thenReturn("harini@gmail.com");
        when(repo.findByUserEmail("harini@gmail.com"))
                .thenReturn(List.of(asset(20, 5000, "harini@gmail.com"))); // 100000

        String result = service.riskScore("token");

        assertEquals("MEDIUM RISK", result);
    }

    @Test
    void testHighRisk() {
        when(jwtUtil.extractEmail("token")).thenReturn("harini@gmail.com");
        when(repo.findByUserEmail("harini@gmail.com"))
                .thenReturn(List.of(asset(100, 5000, "harini@gmail.com"))); // 500000

        String result = service.riskScore("token");

        assertEquals("HIGH RISK", result);
    }

    @Test
    void testEmptyPortfolioLowRisk() {
        when(jwtUtil.extractEmail("token")).thenReturn("harini@gmail.com");
        when(repo.findByUserEmail("harini@gmail.com"))
                .thenReturn(List.of());

        String result = service.riskScore("token");

        assertEquals("LOW RISK", result);
    }

    @Test
    void testSummaryTotal() {
        when(jwtUtil.extractEmail("token")).thenReturn("harini@gmail.com");
        when(repo.findByUserEmail("harini@gmail.com"))
                .thenReturn(List.of(
                        asset(10, 3000, "harini@gmail.com"),
                        asset(5, 2000, "harini@gmail.com")
                )); // 30000 + 10000 = 40000

        Double total = service.getTotal("token");

        assertEquals(40000.0, total);
    }

    @Test
    void testDeleteAuthorized() {
        Portfolio p = asset(10, 3000, "harini@gmail.com");

        when(jwtUtil.extractEmail("token")).thenReturn("harini@gmail.com");
        when(repo.findById(1L)).thenReturn(Optional.of(p));

        String result = service.delete(1L, "token");

        verify(repo, times(1)).delete(p);
        assertEquals("Asset Deleted", result);
    }

    @Test
    void testDeleteUnauthorized() {
        Portfolio p = asset(10, 3000, "other@gmail.com");

        when(jwtUtil.extractEmail("token")).thenReturn("harini@gmail.com");
        when(repo.findById(1L)).thenReturn(Optional.of(p));

        RuntimeException ex =
                assertThrows(RuntimeException.class,
                        () -> service.delete(1L, "token"));

        assertEquals("Unauthorized", ex.getMessage());
    }

    @Test
    void testDeleteAssetNotFound() {
        when(repo.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex =
                assertThrows(RuntimeException.class,
                        () -> service.delete(1L, "token"));

        assertEquals("Asset not found", ex.getMessage());
    }
}