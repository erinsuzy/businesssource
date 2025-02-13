package com.example.businesssource.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "business_plans")
public class BusinessPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(columnDefinition = "TEXT")
    private String companyName;

    @OneToOne(mappedBy = "businessPlan", cascade = CascadeType.ALL)
    private CompanyDescription companyDescription;

    @OneToOne(mappedBy = "businessPlan", cascade = CascadeType.ALL)
    private MarketAnalysis marketAnalysis;

    @OneToOne(mappedBy = "businessPlan", cascade = CascadeType.ALL)
    private OrganizationManagement organizationManagement;

    @OneToOne(mappedBy = "businessPlan", cascade = CascadeType.ALL)
    private ProductsServices productsServices;

    @OneToOne(mappedBy = "businessPlan", cascade = CascadeType.ALL)
    private MarketingStrategy marketingStrategy;

    @OneToOne(mappedBy = "businessPlan", cascade = CascadeType.ALL)
    private FinancialProjections financialProjections;

    @OneToOne(mappedBy = "businessPlan", cascade = CascadeType.ALL)
    private FundingRequest fundingRequest;


    public BusinessPlan(Long id, User user, String companyName, CompanyDescription companyDescription, MarketAnalysis marketAnalysis, OrganizationManagement organizationManagement, ProductsServices productsServices, MarketingStrategy marketingStrategy, FinancialProjections financialProjections, FundingRequest fundingRequest) {
        this.id = id;
        this.user = user;
        this.companyName = companyName;
        this.companyDescription = companyDescription;
        this.marketAnalysis = marketAnalysis;
        this.organizationManagement = organizationManagement;
        this.productsServices = productsServices;
        this.marketingStrategy = marketingStrategy;
        this.financialProjections = financialProjections;
        this.fundingRequest = fundingRequest;
    }

    public BusinessPlan() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public CompanyDescription getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(CompanyDescription companyDescription) {
        this.companyDescription = companyDescription;
    }

    public MarketAnalysis getMarketAnalysis() {
        return marketAnalysis;
    }

    public void setMarketAnalysis(MarketAnalysis marketAnalysis) {
        this.marketAnalysis = marketAnalysis;
    }

    public OrganizationManagement getOrganizationManagement() {
        return organizationManagement;
    }

    public void setOrganizationManagement(OrganizationManagement organizationManagement) {
        this.organizationManagement = organizationManagement;
    }

    public ProductsServices getProductsServices() {
        return productsServices;
    }

    public void setProductsServices(ProductsServices productsServices) {
        this.productsServices = productsServices;
    }

    public MarketingStrategy getMarketingStrategy() {
        return marketingStrategy;
    }

    public void setMarketingStrategy(MarketingStrategy marketingStrategy) {
        this.marketingStrategy = marketingStrategy;
    }

    public FinancialProjections getFinancialProjections() {
        return financialProjections;
    }

    public void setFinancialProjections(FinancialProjections financialProjections) {
        this.financialProjections = financialProjections;
    }

    public FundingRequest getFundingRequest() {
        return fundingRequest;
    }

    public void setFundingRequest(FundingRequest fundingRequest) {
        this.fundingRequest = fundingRequest;
    }
}
