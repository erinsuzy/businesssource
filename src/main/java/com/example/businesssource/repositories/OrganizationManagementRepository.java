package com.example.businesssource.repositories;

import com.example.businesssource.entities.BusinessPlan;
import com.example.businesssource.entities.MarketAnalysis;
import com.example.businesssource.entities.OrganizationManagement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrganizationManagementRepository extends JpaRepository<OrganizationManagement, Long> {
    Optional<OrganizationManagement> findByBusinessPlan(BusinessPlan businessPlan);
}
