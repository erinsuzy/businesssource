package com.example.businesssource.repositories;

import com.example.businesssource.entities.BusinessPlan;
import com.example.businesssource.entities.MarketingStrategy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MarketingStrategyRepository extends JpaRepository<MarketingStrategy, Long> {
    Optional<MarketingStrategy> findByBusinessPlan(BusinessPlan businessPlan);
}
