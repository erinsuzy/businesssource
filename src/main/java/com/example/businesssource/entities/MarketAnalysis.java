package com.example.businesssource.entities;


import jakarta.persistence.*;


@Entity
@Table(name = "market_analysis")
public class MarketAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "business_plan_id", nullable = false)
    private BusinessPlan businessPlan;

    @Column(columnDefinition = "TEXT")
    private String marketOverview;

    public MarketAnalysis() {
    }
    public MarketAnalysis(BusinessPlan businessPlan, String marketOverview) {
        this.businessPlan = businessPlan;
        this.marketOverview = marketOverview;
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

    public String getMarketOverview() {
        return marketOverview;
    }

    public void setMarketOverview(String marketOverview) {
        this.marketOverview = marketOverview;
    }
}
