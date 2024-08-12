package com.example.businesssource.controllers;

import com.example.businesssource.entities.BusinessPlan;
import com.example.businesssource.services.BusinessPlanService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/business-plan")
public class BusinessPlanController {

    @Autowired
    private BusinessPlanService businessPlanService;

    @GetMapping("/create")
    public String createBusinessPlanForm(Model model) {
        model.addAttribute("businessPlan", new BusinessPlan());
        return "business-plan/create"; // Thymeleaf template to start the plan
    }

    @PostMapping("/company-description")
    public String saveCompanyDescription(@ModelAttribute BusinessPlan businessPlan, Model model) {
        businessPlanService.save(businessPlan);
        model.addAttribute("businessPlan", businessPlan);
        return "redirect:/business-plan/market-analysis";
    }

    @GetMapping("/market-analysis")
    public String marketAnalysisForm(@ModelAttribute BusinessPlan businessPlan, Model model) {
        model.addAttribute("businessPlan", businessPlan);
        return "business-plan/market-analysis"; // Thymeleaf template for market analysis
    }

    @PostMapping("/market-analysis")
    public String saveMarketAnalysis(@ModelAttribute BusinessPlan businessPlan, Model model) {
        businessPlanService.save(businessPlan);
        model.addAttribute("businessPlan", businessPlan);
        return "redirect:/business-plan/organization-management";
    }

    @GetMapping("/organization-management")
    public String organizationManagementForm(@ModelAttribute BusinessPlan businessPlan, Model model) {
        model.addAttribute("businessPlan", businessPlan);
        return "business-plan/organization-management"; // Thymeleaf template for organization management
    }

    @PostMapping("/organization-management")
    public String saveOrganizationManagement(@ModelAttribute BusinessPlan businessPlan, Model model) {
        businessPlanService.save(businessPlan);
        model.addAttribute("businessPlan", businessPlan);
        return "redirect:/business-plan/products-services";
    }

    @GetMapping("/products-services")
    public String productsServicesForm(@ModelAttribute BusinessPlan businessPlan, Model model) {
        model.addAttribute("businessPlan", businessPlan);
        return "business-plan/products-services"; // Thymeleaf template for products/services
    }

    @PostMapping("/products-services")
    public String saveProductServices(@ModelAttribute BusinessPlan businessPlan, Model model) {
        businessPlanService.save(businessPlan);
        model.addAttribute("businessPlan", businessPlan);
        return "redirect:/business-plan/marketing-strategy";
    }

    @GetMapping("/marketing-strategy")
    public String marketingStrategyForm(@ModelAttribute BusinessPlan businessPlan, Model model) {
        model.addAttribute("businessPlan", businessPlan);
        return "business-plan/marketing-strategy"; // Thymeleaf template for marketing strategy
    }

    @PostMapping("/marketing-strategy")
    public String saveMarketingStrategy(@ModelAttribute BusinessPlan businessPlan, Model model) {
        businessPlanService.save(businessPlan);
        model.addAttribute("businessPlan", businessPlan);
        return "redirect:/business-plan/funding-request";
    }

    @GetMapping("/funding-request")
    public String fundingRequestForm(@ModelAttribute BusinessPlan businessPlan, Model model) {
        model.addAttribute("businessPlan", businessPlan);
        return "business-plan/funding-request"; // Thymeleaf template for funding request
    }

    @PostMapping("/funding-request")
    public String saveFundingRequest(@ModelAttribute BusinessPlan businessPlan, Model model) {
        businessPlanService.save(businessPlan);
        model.addAttribute("businessPlan", businessPlan);
        return "redirect:/business-plan/financial-projections";
    }

    @GetMapping("/financial-projections")
    public String financialProjectionsForm(@ModelAttribute BusinessPlan businessPlan, Model model) {
        model.addAttribute("businessPlan", businessPlan);
        return "business-plan/financial-projections"; // Thymeleaf template for financial projections
    }

    @PostMapping("/financial-projections")
    public String saveFinancialProjections(@ModelAttribute BusinessPlan businessPlan, Model model) {
        businessPlanService.save(businessPlan);
        model.addAttribute("businessPlan", businessPlan);
        return "redirect:/business-plan/review";
    }

    @GetMapping("/review")
    public String reviewBusinessPlan(@ModelAttribute BusinessPlan businessPlan, Model model) {
        model.addAttribute("businessPlan", businessPlan);
        return "business-plan/review"; // Thymeleaf template for reviewing the full plan
    }
}



