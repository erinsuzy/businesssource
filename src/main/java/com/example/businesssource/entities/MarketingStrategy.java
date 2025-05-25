package com.example.businesssource.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "marketing_strategy")
public class MarketingStrategy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "business_plan_id", nullable = false)
    private BusinessPlan businessPlan;

    @Column(columnDefinition = "TEXT")
    private String strategy;

    public MarketingStrategy() {
    }

    public MarketingStrategy(String strategy, BusinessPlan businessPlan, Long id) {
        this.strategy = strategy;
        this.businessPlan = businessPlan;
        this.id = id;
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

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }
}
