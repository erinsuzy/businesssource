package com.example.businesssource.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "company_description")
public class CompanyDescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "business_plan_id", nullable = false)
    private BusinessPlan businessPlan;

    @Column(columnDefinition = "TEXT")
    private String businessType;

    @Column(columnDefinition = "TEXT")
    private String missionStatement;

    @Column(columnDefinition = "TEXT")
    private String companyGoals;


    public CompanyDescription() {
    }

    public CompanyDescription(Long id, BusinessPlan businessPlan, String businessType, String missionStatement, String companyGoals) {
        this.id = id;
        this.businessPlan = businessPlan;
        this.businessType = businessType;
        this.missionStatement = missionStatement;
        this.companyGoals = companyGoals;
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

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getMissionStatement() {
        return missionStatement;
    }

    public void setMissionStatement(String missionStatement) {
        this.missionStatement = missionStatement;
    }

    public String getCompanyGoals() {
        return companyGoals;
    }

    public void setCompanyGoals(String companyGoals) {
        this.companyGoals = companyGoals;
    }
}
