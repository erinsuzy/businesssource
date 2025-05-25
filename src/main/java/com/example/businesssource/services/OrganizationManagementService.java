package com.example.businesssource.services;

import com.example.businesssource.entities.BusinessPlan;
import com.example.businesssource.entities.OrganizationManagement;
import com.example.businesssource.repositories.OrganizationManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrganizationManagementService {

    private final OrganizationManagementRepository organizationManagementRepository;

    @Autowired
    public OrganizationManagementService(OrganizationManagementRepository organizationManagementRepository) {
        this.organizationManagementRepository = organizationManagementRepository;
    }

    public Optional<OrganizationManagement> getByBusinessPlan(BusinessPlan businessPlan){
        return  organizationManagementRepository.findByBusinessPlan(businessPlan);
    }

    public OrganizationManagement saveOrUpdate(OrganizationManagement organizationManagement){
        return organizationManagementRepository.save(organizationManagement);
    }
}
