package com.example.businesssource.repositories;

import com.example.businesssource.entities.BusinessPlan;
import com.example.businesssource.entities.FundingRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FundingRequestRepository extends JpaRepository<FundingRequest, Long> {
    Optional<FundingRequest> findByBusinessPlan(BusinessPlan businessPlan);
}
