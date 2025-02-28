package com.example.businesssource.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "business_plans")
public class BusinessPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(columnDefinition = "TEXT")
    private String companyName;

    @OneToOne(mappedBy = "businessPlan", cascade = CascadeType.ALL)
    private CompanyDescription companyDescription;

    @OneToOne(mappedBy = "businessPlan", cascade = CascadeType.ALL)
    private String marketAnalysis;

    @OneToOne(mappedBy = "businessPlan", cascade = CascadeType.ALL)
    private String organizationManagement;

    @OneToOne(mappedBy = "businessPlan", cascade = CascadeType.ALL)
    private String productsServices;

    @OneToOne(mappedBy = "businessPlan", cascade = CascadeType.ALL)
    private String marketingStrategy;

    @OneToOne(mappedBy = "businessPlan", cascade = CascadeType.ALL)
    private String financialProjections;

    @OneToOne(mappedBy = "businessPlan", cascade = CascadeType.ALL)
    private FundingRequest fundingRequest;


    public BusinessPlan(Long id, User user, String companyName, CompanyDescription companyDescription, MarketAnalysis marketAnalysis, OrganizationManagement organizationManagement, ProductsServices productsServices, MarketingStrategy marketingStrategy, FinancialProjections financialProjections, FundingRequest fundingRequest) {
        this.id = id;
        this.user = user;
        this.companyName = companyName;
        this.companyDescription = companyDescription;
        this.marketAnalysis = String.valueOf(marketAnalysis);
        this.organizationManagement = String.valueOf(organizationManagement);
        this.productsServices = String.valueOf(productsServices);
        this.marketingStrategy = String.valueOf(marketingStrategy);
        this.financialProjections = financialProjections.toString();
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

    public String getFinancialProjections() {
        return financialProjections;
    }

    public void setFinancialProjections(String financialProjections) {
        this.financialProjections = financialProjections;
    }

    public FundingRequest getFundingRequest() {
        return fundingRequest;
    }

    public void setFundingRequest(String fundingRequest) {
        this.fundingRequest = fundingRequest;
    }


}
