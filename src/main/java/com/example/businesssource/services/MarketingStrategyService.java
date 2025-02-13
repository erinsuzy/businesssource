package com.example.businesssource.services;

import com.example.businesssource.entities.BusinessPlan;
import com.example.businesssource.entities.MarketingStrategy;
import com.example.businesssource.repositories.MarketingStrategyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MarketingStrategyService {

    private final MarketingStrategyRepository marketingStrategyRepository;

    @Autowired
    public MarketingStrategyService(MarketingStrategyRepository marketingStrategyRepository) {
        this.marketingStrategyRepository = marketingStrategyRepository;
    }

    public Optional<MarketingStrategy> getByBusinessPlan(BusinessPlan businessPlan) {
        return  marketingStrategyRepository.findByBusinessPlan(businessPlan);
    }

    public MarketingStrategy saveOrUpdate(MarketingStrategy marketingStrategy) {
        return  marketingStrategyRepository.save(marketingStrategy);
    }
}
