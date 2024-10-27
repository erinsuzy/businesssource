package com.example.businesssource.controllers;

import com.example.businesssource.entities.BusinessPlan;
import com.example.businesssource.services.BusinessPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/business-plan")
public class BusinessPlanController {

    private final BusinessPlanService businessPlanService;

    public BusinessPlanController(BusinessPlanService businessPlanService) {
        this.businessPlanService = businessPlanService;
    }


    @GetMapping("/create")
    public String createBusinessPlanForm(Model model) {
        model.addAttribute("businessPlan", new BusinessPlan());
        return "business-plan/create";
    }

    @PostMapping("/create")
    public String saveBusinessPlan(@ModelAttribute BusinessPlan businessPlan, Model model) {
        BusinessPlan savedBusinessPlan = businessPlanService.save(businessPlan);
        model.addAttribute("businessPlan", savedBusinessPlan);
        return "redirect:/business-plan/company-description?planId=" + savedBusinessPlan.getId();
    }

    @GetMapping("/company-description")
    public String createCompanyDescription(@RequestParam("planId") Long planId, Model model, RedirectAttributes redirectAttributes) {
        Optional<BusinessPlan> optionalPlan = businessPlanService.findById(planId);
        if (optionalPlan.isPresent()) {
            model.addAttribute("businessPlan", optionalPlan.get());
            model.addAttribute("content", "business-plan/company-description :: content");
            model.addAttribute("pageTitle", "Company Description"); // Set the page title
            return "fragments/layout"; // Specify layout location
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Business plan not found. Please create a new business plan.");
            return "redirect:/business-plan/create";
        }
    }


    @PostMapping("/company-description")
    public String saveCompanyDescription(@ModelAttribute BusinessPlan businessPlan, Model model) {
        BusinessPlan savedPlan = businessPlanService.save(businessPlan); // Save the business plan to the database
        model.addAttribute("businessPlan", savedPlan);
        return "redirect:/business-plan/market-analysis?planId=" + savedPlan.getId(); // Pass the plan ID to the next step
    }

    @GetMapping("/market-analysis")
    public String marketAnalysisForm(@RequestParam("planId") Long planId, Model model, RedirectAttributes redirectAttributes) {
        Optional<BusinessPlan> optionalPlan = businessPlanService.findById(planId);
        if (optionalPlan.isPresent()) {
            model.addAttribute("businessPlan", optionalPlan.get());
            model.addAttribute("content", "business-plan/market-analysis :: content");
            model.addAttribute("pageTitle", "Market Analysis");
            return "business-plan/market-analysis"; // Thymeleaf template for market analysis
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Business plan not found. Please create a new business plan.");
            return "redirect:/business-plan/create"; // Handle the case where the plan is not found
        }
    }

    @PostMapping("/market-analysis")
    public String saveMarketAnalysis(@ModelAttribute BusinessPlan businessPlan, Model model) {
        BusinessPlan savedPlan = businessPlanService.save(businessPlan); // Save the business plan after this step
        model.addAttribute("businessPlan", savedPlan);
        return "redirect:/business-plan/organization-management?planId=" + savedPlan.getId(); // Pass the plan ID to the next step
    }

    @GetMapping("/organization-management")
    public String organizationManagementForm(@RequestParam("planId") Long planId, Model model, RedirectAttributes redirectAttributes) {
        Optional<BusinessPlan> optionalPlan = businessPlanService.findById(planId);
        if (optionalPlan.isPresent()) {
            model.addAttribute("businessPlan", optionalPlan.get());
            model.addAttribute("content", "business-plan/organization-management :: content");
            model.addAttribute("pageTitle", "Organization Management");
            return "business-plan/organization-management";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Business plan not found. Please create a new business plan.");
            return "redirect:/business-plan/create";
        }
    }

   @PostMapping("/organization-management")
   public String saveOrganizationManagement(@ModelAttribute BusinessPlan businessPlan, Model model) {
        BusinessPlan savedPlan = businessPlanService.save(businessPlan);
        model.addAttribute("businessPlan", savedPlan);
        return "redirect:/business-plan/products-services?planId=" + savedPlan.getId();
   }

   @GetMapping("/products-services")
   public String productsServicesForm(@RequestParam("planId") Long planId, Model model, RedirectAttributes redirectAttributes) {
       Optional<BusinessPlan> optionalPlan = businessPlanService.findById(planId);
       if (optionalPlan.isPresent()) {
           model.addAttribute("businessPlan", optionalPlan.get());
           model.addAttribute("content", "business-plan/products-services :: content");
           model.addAttribute("pageTitle", "Products and Services");
           return "business-plan/products-services";
       } else {
           redirectAttributes.addFlashAttribute("errorMessage", "Business plan not found. Please create a new business plan.");
           return "redirect:/business-plan/create";
       }
   }

   @PostMapping("/products-services")
   public String saveProductsServices(@ModelAttribute BusinessPlan businessPlan, Model model) {
        BusinessPlan savedPlan = businessPlanService.save(businessPlan);
        model.addAttribute("businessPlan", savedPlan);
        return "redirect:/business-plan/marketing-strategy?planId=" + savedPlan.getId();
   }

   @GetMapping("/marketing-strategy")
   public String marketingStrategyForm(@RequestParam("planId") Long planId, Model model, RedirectAttributes redirectAttributes) {
       Optional<BusinessPlan> optionalPlan = businessPlanService.findById(planId);
       if (optionalPlan.isPresent()) {
           model.addAttribute("businessPlan", optionalPlan.get());
           model.addAttribute("content", "business-plan/marketing-strategy :: content");
           model.addAttribute("pageTitle", "Marketing Strategy");
           return "business-plan/marketing-strategy";
       } else {
           redirectAttributes.addFlashAttribute("errorMessage", "Business plan not found. Please create a new business plan.");
           return "redirect:/business-plan/create";
       }
   }

   @PostMapping("/marketing-strategy")
   public String saveMarketingStrategy(@ModelAttribute BusinessPlan businessPlan, Model model) {
        BusinessPlan savedPlan = businessPlanService.save(businessPlan);
        model.addAttribute("businessPlan", savedPlan);
        return "redirect:/business-plan/financial-projections?planId=" + savedPlan.getId();
   }

   @GetMapping("/financial-projections")
   public String financialProjectionsForm(@RequestParam("planId") Long planId, Model model, RedirectAttributes redirectAttributes) {
        Optional<BusinessPlan> optionalPlan = businessPlanService.findById(planId);
        if (optionalPlan.isPresent()) {
            model.addAttribute("businessPlan", optionalPlan.get());
            model.addAttribute("content", "business-plan/financial-projections :: content");
            model.addAttribute("pageTitle", "Financial Projections");
            return "business-plan/financial-projections";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Business plan not found. Please create a new business plan.");
            return "redirect:/business-plan/create";
        }
   }

   @PostMapping("/financial-projections")
   public String saveFinancialProjections(@ModelAttribute BusinessPlan businessPlan, Model model) {
        BusinessPlan savedPlan = businessPlanService.save(businessPlan);
        model.addAttribute("businessPlan", savedPlan);
        return "redirect:/business-plan/funding-request?planId=" + savedPlan.getId();
   }

   @GetMapping("/funding-request")
   public String fundingRequestForm(@RequestParam("planId") Long planId, Model model, RedirectAttributes redirectAttributes) {
        Optional<BusinessPlan> optionalPlan = businessPlanService.findById(planId);
        if (optionalPlan.isPresent()) {
            model.addAttribute("businessPlan", optionalPlan.get());
            model.addAttribute("content", "business-plan/funding-request :: content");
            model.addAttribute("pageTitle", "Funding Request");
            return "business-plan/funding-request";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Business plan not found. Please create a new business plan.");
            return "redirect:/business-plan/create";
        }
   }

   @PostMapping("/funding-request")
   public String saveFundingRequest(@ModelAttribute BusinessPlan businessPlan, Model model) {
        BusinessPlan savedPlan = businessPlanService.save(businessPlan);
        model.addAttribute("businessPlan", savedPlan);
        return "redirect:/business-plan/review?planId=" + savedPlan.getId();
   }

    @GetMapping("/review")
    public String reviewBusinessPlan(@RequestParam("planId") Long planId, Model model, RedirectAttributes redirectAttributes) {
        Optional<BusinessPlan> optionalPlan = businessPlanService.findById(planId);
        if (optionalPlan.isPresent()) {
            model.addAttribute("businessPlan", optionalPlan.get());
            return "business-plan/review"; // Thymeleaf template for reviewing the full plan
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Business plan not found. Please create a new business plan.");
            return "redirect:/business-plan/create"; // Handle the case where the plan is not found
        }
    }
}




