package com.example.businesssource.repositories;

import com.example.businesssource.entities.BusinessPlan;
import com.example.businesssource.entities.FinancialProjections;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FinancialProjectionsRepository extends JpaRepository<FinancialProjections, Long> {
    Optional<FinancialProjections> findByBusinessPlan(BusinessPlan businessPlan);
}
