package com.example.businesssource.services;

import com.example.businesssource.entities.BusinessPlan;
import com.example.businesssource.entities.FundingRequest;
import com.example.businesssource.entities.ProductsServices;
import com.example.businesssource.entities.User;
import com.example.businesssource.repositories.FundingRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Service
public class FundingRequestService {
    private final FundingRequestRepository fundingRequestRepository;

    @Autowired
    public FundingRequestService(FundingRequestRepository fundingRequestRepository) {
        this.fundingRequestRepository = fundingRequestRepository;
    }

    public FundingRequest saveOrUpdate(FundingRequest fundingRequest) {
        return fundingRequestRepository.save(fundingRequest);
    }

    public Optional<FundingRequest> getByBusinessPlan(BusinessPlan businessPlan) {
        return fundingRequestRepository.findByBusinessPlan(businessPlan);
    }


}
