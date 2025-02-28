package com.example.businesssource.services;

import com.example.businesssource.entities.BusinessPlan;
import com.example.businesssource.entities.FinancialProjections;
import com.example.businesssource.repositories.FinancialProjectionsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class FinancialProjectionsService {
    private final FinancialProjectionsRepository financialProjectionsRepository;

    @Autowired
  public FinancialProjectionsService(FinancialProjectionsRepository financialProjectionsRepository) {
        this.financialProjectionsRepository = financialProjectionsRepository;
    }

    public FinancialProjections saveOrUpdate(FinancialProjections financialProjections) {
        return financialProjectionsRepository.save(financialProjections);
    }

    public Optional<FinancialProjections> getByBusinessPlan(BusinessPlan businessPlan) {
        return financialProjectionsRepository.findByBusinessPlan(businessPlan);
    }

}


