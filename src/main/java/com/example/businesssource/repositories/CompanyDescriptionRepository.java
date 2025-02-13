package com.example.businesssource.repositories;

import com.example.businesssource.entities.BusinessPlan;
import com.example.businesssource.entities.CompanyDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyDescriptionRepository extends JpaRepository<CompanyDescription, Long> {
    Optional<CompanyDescription> findByBusinessPlan(BusinessPlan businessPlan);
}

