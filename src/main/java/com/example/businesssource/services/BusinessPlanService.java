package com.example.businesssource.services;

import com.example.businesssource.entities.BusinessPlan;
import com.example.businesssource.repositories.BusinessPlanRepository;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;


import java.io.OutputStream;

import java.util.List;
import java.util.Optional;

@Service
public class BusinessPlanService {

    @Autowired
    private BusinessPlanRepository businessPlanRepository;

    public BusinessPlan save(BusinessPlan businessPlan) {
        System.out.println("Saving Business Plan: " + businessPlan.getCompanyName());
        return businessPlanRepository.save(businessPlan);
    }


    public Optional<BusinessPlan> findById(Long id) {
        return businessPlanRepository.findById(id);
    }

    public List<BusinessPlan> findAll() {
        return businessPlanRepository.findAll();
    }

    public void deleteBusinessPlan(Long id) {
        BusinessPlan plan = businessPlanRepository.findById(id).orElseThrow();
        businessPlanRepository.delete(plan);
    }



    public BusinessPlan findBusinessPlanById(Long id) {
        return businessPlanRepository.findById(id).orElse(null);
    }

    public BusinessPlan getPlanWithAllSections(Long planId) {
        BusinessPlan plan = businessPlanRepository.findById(planId)
                .orElseThrow(() -> new RuntimeException("Plan not found"));


        Hibernate.initialize(plan.getCompanyDescription());
        Hibernate.initialize(plan.getMarketAnalysis());
        Hibernate.initialize(plan.getProductsServices());
        Hibernate.initialize(plan.getFundingRequest());
        Hibernate.initialize(plan.getOrganizationManagement());
        Hibernate.initialize(plan.getMarketingStrategy());
        Hibernate.initialize(plan.getFinancialProjections());


        return plan;
    }

}




