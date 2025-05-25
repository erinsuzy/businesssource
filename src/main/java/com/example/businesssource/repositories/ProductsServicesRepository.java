package com.example.businesssource.repositories;


import com.example.businesssource.entities.BusinessPlan;
import com.example.businesssource.entities.ProductsServices;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductsServicesRepository extends JpaRepository<ProductsServices, Long> {
    Optional<ProductsServices> findByBusinessPlan(BusinessPlan businessPlan);
}
