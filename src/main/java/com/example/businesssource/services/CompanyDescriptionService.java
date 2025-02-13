package com.example.businesssource.services;

import com.example.businesssource.entities.BusinessPlan;
import com.example.businesssource.entities.CompanyDescription;
import com.example.businesssource.repositories.CompanyDescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyDescriptionService {
    private final CompanyDescriptionRepository companyDescriptionRepository;

    @Autowired
    public CompanyDescriptionService(CompanyDescriptionRepository companyDescriptionRepository) {
        this.companyDescriptionRepository = companyDescriptionRepository;
    }

    public CompanyDescription saveOrUpdate(CompanyDescription companyDescription) {
        return companyDescriptionRepository.save(companyDescription);
    }

    public Optional<CompanyDescription> getByBusinessPlan(BusinessPlan businessPlan) {
        return companyDescriptionRepository.findByBusinessPlan(businessPlan);
    }
}
