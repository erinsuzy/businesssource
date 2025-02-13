package com.example.businesssource.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "products_services")
public class ProductsServices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "business_plan_id", nullable = false)
    private BusinessPlan businessPlan;

    @Column(columnDefinition = "TEXT")
    private String description;

    public ProductsServices() {
    }

    public ProductsServices(BusinessPlan businessPlan, String description) {
        this.businessPlan = businessPlan;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BusinessPlan getBusinessPlan() {
        return businessPlan;
    }

    public void setBusinessPlan(BusinessPlan businessPlan) {
        this.businessPlan = businessPlan;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
