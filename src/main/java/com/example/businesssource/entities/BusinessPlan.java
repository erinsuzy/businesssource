package com.example.businesssource.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "business_plan")
public class BusinessPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String companyName;

    @Column(columnDefinition = "TEXT")
    private String companyDescription;

    @Column(columnDefinition = "TEXT")
    private String missionStatement;

    @Column(columnDefinition = "TEXT")
    private String marketAnalysis;

    @Column(columnDefinition = "TEXT")
    private String organizationManagement;

    @Column(columnDefinition = "TEXT")
    private String productsServices;

    @Column(columnDefinition = "TEXT")
    private String marketingStrategy;

    @Column(columnDefinition = "TEXT")
    private String fundingRequest;

    @Column(columnDefinition = "TEXT")
    private String financialProjections;

    @Column(columnDefinition = "TEXT")
    private String appendix;

    private Timestamp createdAt;
    private Timestamp updatedAt;




    public BusinessPlan(Long id, String companyName, String companyDescription, String missionStatement, String marketAnalysis, String organizationManagement, String productsServices, String marketingStrategy, String fundingRequest, String financialProjections, String appendix, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.companyName = companyName;
        this.companyDescription = companyDescription;
        this.missionStatement = missionStatement;
        this.marketAnalysis = marketAnalysis;
        this.organizationManagement = organizationManagement;
        this.productsServices = productsServices;
        this.marketingStrategy = marketingStrategy;
        this.fundingRequest = fundingRequest;
        this.financialProjections = financialProjections;
        this.appendix = appendix;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public BusinessPlan() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

    public String getMissionStatement() {
        return missionStatement;
    }

    public void setMissionStatement(String missionStatement) {
        this.missionStatement = missionStatement;
    }

    public String getMarketAnalysis() {
        return marketAnalysis;
    }

    public void setMarketAnalysis(String marketAnalysis) {
        this.marketAnalysis = marketAnalysis;
    }

    public String getOrganizationManagement() {
        return organizationManagement;
    }

    public void setOrganizationManagement(String organizationManagement) {
        this.organizationManagement = organizationManagement;
    }

    public String getProductsServices() {
        return productsServices;
    }

    public void setProductsServices(String productsServices) {
        this.productsServices = productsServices;
    }

    public String getMarketingStrategy() {
        return marketingStrategy;
    }

    public void setMarketingStrategy(String marketingStrategy) {
        this.marketingStrategy = marketingStrategy;
    }

    public String getFundingRequest() {
        return fundingRequest;
    }

    public void setFundingRequest(String fundingRequest) {
        this.fundingRequest = fundingRequest;
    }

    public String getFinancialProjections() {
        return financialProjections;
    }

    public void setFinancialProjections(String financialProjections) {
        this.financialProjections = financialProjections;
    }

    public String getAppendix() {
        return appendix;
    }

    public void setAppendix(String appendix) {
        this.appendix = appendix;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}

