package com.example.businesssource.repositories;

import com.example.businesssource.entities.BusinessPlan;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusinessPlanRepository extends JpaRepository<BusinessPlan, Long> {
    @EntityGraph(attributePaths = {
            "companyDescription",
            "marketAnalysis",
            "organizationManagement",
            "productsServices",
            "marketingStrategy",
            "financialProjections",
            "fundingRequest"
    })
    Optional<BusinessPlan> findWithAllSectionsById(Long id);

}
