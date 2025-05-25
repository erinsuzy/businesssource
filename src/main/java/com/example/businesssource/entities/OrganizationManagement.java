package com.example.businesssource.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "organization_management")
public class OrganizationManagement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "business_plan_id", nullable = false)
    private BusinessPlan businessPlan;

    @Column(columnDefinition = "TEXT")
    private String structure;

    public OrganizationManagement() {
    }

    public OrganizationManagement(Long id, BusinessPlan businessPlan, String structure) {
        this.id = id;
        this.businessPlan = businessPlan;
        this.structure = structure;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BusinessPlan getBusinessPlan() {
        return businessPlan;
    }

    public void setBusinessPlan(BusinessPlan businessPlan) {
        this.businessPlan = businessPlan;
    }

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }
}
