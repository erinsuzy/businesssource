package com.example.businesssource.services;

import com.example.businesssource.entities.BusinessPlan;
import com.example.businesssource.repositories.BusinessPlanRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class BusinessPlanService {

    @Autowired
    private BusinessPlanRepository businessPlanRepository;

    public void save(BusinessPlan businessPlan) {
        businessPlanRepository.save(businessPlan);
    }

    public BusinessPlan findById(Long id) {
        return businessPlanRepository.findById(id).orElse(null);
    }

    // Additional methods as needed
}


