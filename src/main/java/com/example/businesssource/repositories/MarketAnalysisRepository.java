package com.example.businesssource.repositories;

import com.example.businesssource.entities.BusinessPlan;
import com.example.businesssource.entities.MarketAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MarketAnalysisRepository extends JpaRepository<MarketAnalysis, Long> {
    Optional<MarketAnalysis> findByBusinessPlan(BusinessPlan businessPlan);
}
