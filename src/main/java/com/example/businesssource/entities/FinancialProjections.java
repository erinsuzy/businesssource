package com.example.businesssource.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "financial_projections")
public class FinancialProjections {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "business_plan_id", nullable = false)
    private BusinessPlan businessPlan;

    @Column(columnDefinition = "TEXT")
    private String forecast;

    public FinancialProjections() {
    }

    public FinancialProjections(Long id, BusinessPlan businessPlan, String forecast) {
        this.id = id;
        this.businessPlan = businessPlan;
        this.forecast = forecast;
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

    public String getForecast() {
        return forecast;
    }

    public void setForecast(String forecast) {
        this.forecast = forecast;
    }
}
