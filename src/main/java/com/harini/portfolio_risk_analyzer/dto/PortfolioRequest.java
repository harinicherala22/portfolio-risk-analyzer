package com.harini.portfolio_risk_analyzer.dto;

import lombok.Data;

@Data
public class PortfolioRequest {

    private String assetName;
    private Integer quantity;
    private Double buyPrice;
}