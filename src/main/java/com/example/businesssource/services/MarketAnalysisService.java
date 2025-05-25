package com.example.businesssource.services;

import com.example.businesssource.entities.BusinessPlan;
import com.example.businesssource.entities.MarketAnalysis;
import com.example.businesssource.repositories.MarketAnalysisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MarketAnalysisService {
    private final MarketAnalysisRepository marketAnalysisRepository;

    @Autowired
    public MarketAnalysisService(MarketAnalysisRepository marketAnalysisRepository) {
        this.marketAnalysisRepository = marketAnalysisRepository;
    }

    public Optional<MarketAnalysis> getByBusinessPlan(BusinessPlan businessPlan) {
        return marketAnalysisRepository.findByBusinessPlan(businessPlan);
    }

    public MarketAnalysis saveOrUpdate(MarketAnalysis marketAnalysis) {
        return marketAnalysisRepository.save(marketAnalysis);
    }
}
