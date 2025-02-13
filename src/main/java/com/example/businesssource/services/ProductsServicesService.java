package com.example.businesssource.services;

import com.example.businesssource.entities.BusinessPlan;
import com.example.businesssource.entities.ProductsServices;
import com.example.businesssource.repositories.ProductsServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductsServicesService {

    private final ProductsServicesRepository productsServicesRepository;

    @Autowired
    public ProductsServicesService(ProductsServicesRepository productsServicesRepository) {
        this.productsServicesRepository = productsServicesRepository;
    }

    public Optional<ProductsServices> getByBusinessPlan(BusinessPlan businessPlan) {
        return productsServicesRepository.findByBusinessPlan(businessPlan);
    }

    public ProductsServices saveOrUpdate(ProductsServices productsServices) {
        return productsServicesRepository.save(productsServices);
    }
}
