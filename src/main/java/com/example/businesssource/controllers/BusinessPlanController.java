package com.example.businesssource.controllers;

import com.example.businesssource.entities.BusinessPlan;
import com.example.businesssource.services.BusinessPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/business-plan")
public class BusinessPlanController {

    private final BusinessPlanService businessPlanService;

    public BusinessPlanController(BusinessPlanService businessPlanService) {
        this.businessPlanService = businessPlanService;
    }
    @GetMapping("/create")
    public String createBusinessPlan(Model model) {
        model.addAttribute("businessPlan", new BusinessPlan());
        return "business-plan/create";
    }

    @PostMapping("/create")
    public String saveBusinessPlan(@ModelAttribute BusinessPlan businessPlan, RedirectAttributes redirectAttributes) {
        businessPlan.setStatus("draft");
        BusinessPlan savedPlan = businessPlanService.save(businessPlan);
        redirectAttributes.addFlashAttribute("message", "Progress saved! You can return to this plan anytime.");
        return "redirect:/business-plan/company-description?planId=" + savedPlan.getId();
    }

    @GetMapping("/company-description")
    public String createCompanyDescription(@RequestParam("planId") Long planId, Model model, RedirectAttributes redirectAttributes) {
        Optional<BusinessPlan> optionalPlan = businessPlanService.findById(planId);
        if (optionalPlan.isPresent()) {
            model.addAttribute("businessPlan", optionalPlan.get());
            model.addAttribute("pageTitle", "Company Description"); // Set the page title
            return "business-plan/company-description";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Business plan not found. Please create a new business plan.");
            return "redirect:/business-plan/create";
        }
    }

    @PostMapping("/company-description")
    public String saveCompanyDescription(
            @RequestParam("planId") Long planId,
            @RequestParam("companyDescription") String companyDescription,
            @RequestParam(value = "status", defaultValue = "IN_PROGRESS") String status,
            @RequestParam("saveOption") String saveOption, // "exit" or "continue"
            RedirectAttributes redirectAttributes) {

        Optional<BusinessPlan> optionalPlan = businessPlanService.findById(planId);
        if (optionalPlan.isPresent()) {
            BusinessPlan businessPlan = optionalPlan.get();
            businessPlan.setCompanyDescription(companyDescription);
            businessPlan.setStatus(status);
            businessPlanService.save(businessPlan);

            redirectAttributes.addFlashAttribute("successMessage", "Company description saved successfully!");

            // Redirect based on save option
            if ("exit".equals(saveOption)) {
                return "redirect:/business-plan/dashboard"; // Redirect to dashboard or home page
            } else {
                return "redirect:/business-plan/market-analysis?planId=" + planId; // Redirect to the next section
            }
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Business plan not found.");
            return "redirect:/business-plan/create";
        }
    }



    @GetMapping("/market-analysis")
    public String marketAnalysisForm(@RequestParam("planId") Long planId, Model model, RedirectAttributes redirectAttributes) {
        Optional<BusinessPlan> optionalPlan = businessPlanService.findById(planId);
        if (optionalPlan.isPresent()) {
            model.addAttribute("businessPlan", optionalPlan.get());
            model.addAttribute("pageTitle", "Market Analysis");
            return "business-plan/market-analysis"; // Thymeleaf template for market analysis
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Business plan not found. Please create a new business plan.");
            return "redirect:/business-plan/create"; // Handle the case where the plan is not found
        }
    }

    @PostMapping("/market-analysis")
    public String saveMarketAnalysis(
            @RequestParam("planId") Long planId,
            @RequestParam("marketAnalysis") String marketAnalysis,
            @RequestParam(value = "status", defaultValue = "IN_PROGRESS") String status,
            @RequestParam("saveOption") String saveOption, // "exit" or "continue"
            RedirectAttributes redirectAttributes) {

        Optional<BusinessPlan> optionalPlan = businessPlanService.findById(planId);
        if (optionalPlan.isPresent()) {
            BusinessPlan businessPlan = optionalPlan.get();
            businessPlan.setMarketAnalysis(marketAnalysis);
            businessPlan.setStatus(status);
            businessPlanService.save(businessPlan);

            redirectAttributes.addFlashAttribute("successMessage", "Market analysis saved successfully!");

            // Redirect based on save option
            if ("exit".equals(saveOption)) {
                return "redirect:/business-plan/dashboard"; // Redirect to dashboard or home page
            } else {
                return "redirect:/business-plan/organization-management?planId=" + planId; // Redirect to the next section
            }
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Business plan not found.");
            return "redirect:/business-plan/create";
        }
    }


    @GetMapping("/organization-management")
    public String organizationManagementForm(@RequestParam("planId") Long planId, Model model, RedirectAttributes redirectAttributes) {
        Optional<BusinessPlan> optionalPlan = businessPlanService.findById(planId);
        if (optionalPlan.isPresent()) {
            model.addAttribute("businessPlan", optionalPlan.get());
            model.addAttribute("pageTitle", "Organization Management");
            return "business-plan/organization-management";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Business plan not found. Please create a new business plan.");
            return "redirect:/business-plan/create";
        }
    }

    @PostMapping("/organization-management")
    public String saveOrganizationManagment(
            @RequestParam("planId") Long planId,
            @RequestParam("organizationManagement") String organizationManagement,
            @RequestParam(value = "status", defaultValue = "IN_PROGRESS") String status,
            @RequestParam("saveOption") String saveOption, // "exit" or "continue"
            RedirectAttributes redirectAttributes) {

        Optional<BusinessPlan> optionalPlan = businessPlanService.findById(planId);
        if (optionalPlan.isPresent()) {
            BusinessPlan businessPlan = optionalPlan.get();
            businessPlan.setOrganizationManagement(organizationManagement);
            businessPlan.setStatus(status);
            businessPlanService.save(businessPlan);

            redirectAttributes.addFlashAttribute("successMessage", "Organization and management saved successfully!");

            // Redirect based on save option
            if ("exit".equals(saveOption)) {
                return "redirect:/business-plan/dashboard"; // Redirect to dashboard or home page
            } else {
                return "redirect:/business-plan/products-services?planId=" + planId; // Redirect to the next section
            }
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Business plan not found.");
            return "redirect:/business-plan/create";
        }
    }


    @GetMapping("/products-services")
   public String productsServicesForm(@RequestParam("planId") Long planId, Model model, RedirectAttributes redirectAttributes) {
       Optional<BusinessPlan> optionalPlan = businessPlanService.findById(planId);
       if (optionalPlan.isPresent()) {
           model.addAttribute("businessPlan", optionalPlan.get());
           model.addAttribute("pageTitle", "Products and Services");
           return "business-plan/products-services";
       } else {
           redirectAttributes.addFlashAttribute("errorMessage", "Business plan not found. Please create a new business plan.");
           return "redirect:/business-plan/create";
       }
   }

    @PostMapping("/products-services")
    public String saveProductsServices(
            @RequestParam("planId") Long planId,
            @RequestParam("productsServices") String productsServices,
            @RequestParam(value = "status", defaultValue = "IN_PROGRESS") String status,
            @RequestParam("saveOption") String saveOption, // "exit" or "continue"
            RedirectAttributes redirectAttributes) {

        Optional<BusinessPlan> optionalPlan = businessPlanService.findById(planId);
        if (optionalPlan.isPresent()) {
            BusinessPlan businessPlan = optionalPlan.get();
            businessPlan.setProductsServices(productsServices);
            businessPlan.setStatus(status);
            businessPlanService.save(businessPlan);

            redirectAttributes.addFlashAttribute("successMessage", "Products and services saved successfully!");

            // Redirect based on save option
            if ("exit".equals(saveOption)) {
                return "redirect:/business-plan/dashboard"; // Redirect to dashboard or home page
            } else {
                return "redirect:/business-plan/marketing-strategy?planId=" + planId; // Redirect to the next section
            }
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Business plan not found.");
            return "redirect:/business-plan/create";
        }
    }


    @GetMapping("/marketing-strategy")
   public String marketingStrategyForm(@RequestParam("planId") Long planId, Model model, RedirectAttributes redirectAttributes) {
       Optional<BusinessPlan> optionalPlan = businessPlanService.findById(planId);
       if (optionalPlan.isPresent()) {
           model.addAttribute("businessPlan", optionalPlan.get());
           model.addAttribute("pageTitle", "Marketing Strategy");
           return "business-plan/marketing-strategy";
       } else {
           redirectAttributes.addFlashAttribute("errorMessage", "Business plan not found. Please create a new business plan.");
           return "redirect:/business-plan/create";
       }
   }

    @PostMapping("/marketing-strategy")
    public String saveMarketingStrategy(
            @RequestParam("planId") Long planId,
            @RequestParam("marketingStrategy") String marketingStrategy,
            @RequestParam(value = "status", defaultValue = "IN_PROGRESS") String status,
            @RequestParam("saveOption") String saveOption, // "exit" or "continue"
            RedirectAttributes redirectAttributes) {

        Optional<BusinessPlan> optionalPlan = businessPlanService.findById(planId);
        if (optionalPlan.isPresent()) {
            BusinessPlan businessPlan = optionalPlan.get();
            businessPlan.setMarketingStrategy(marketingStrategy);
            businessPlan.setStatus(status);
            businessPlanService.save(businessPlan);

            redirectAttributes.addFlashAttribute("successMessage", "Marketing strategy saved successfully!");

            // Redirect based on save option
            if ("exit".equals(saveOption)) {
                return "redirect:/business-plan/dashboard"; // Redirect to dashboard or home page
            } else {
                return "redirect:/business-plan/financial-projections?planId=" + planId; // Redirect to the next section
            }
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Business plan not found.");
            return "redirect:/business-plan/create";
        }
    }



    @GetMapping("/financial-projections")
   public String financialProjectionsForm(@RequestParam("planId") Long planId, Model model, RedirectAttributes redirectAttributes) {
        Optional<BusinessPlan> optionalPlan = businessPlanService.findById(planId);
        if (optionalPlan.isPresent()) {
            model.addAttribute("businessPlan", optionalPlan.get());
            model.addAttribute("pageTitle", "Financial Projections");
            return "business-plan/financial-projections";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Business plan not found. Please create a new business plan.");
            return "redirect:/business-plan/create";
        }
   }

    @PostMapping("/financial-projections")
    public String saveFinancialProjections(
            @RequestParam("planId") Long planId,
            @RequestParam("financialProjections") String financialProjections,
            @RequestParam(value = "status", defaultValue = "IN_PROGRESS") String status,
            @RequestParam("saveOption") String saveOption, // "exit" or "continue"
            RedirectAttributes redirectAttributes) {

        Optional<BusinessPlan> optionalPlan = businessPlanService.findById(planId);
        if (optionalPlan.isPresent()) {
            BusinessPlan businessPlan = optionalPlan.get();
            businessPlan.setFinancialProjections(financialProjections);
            businessPlan.setStatus(status);
            businessPlanService.save(businessPlan);

            redirectAttributes.addFlashAttribute("successMessage", "Financial projections saved successfully!");

            // Redirect based on save option
            if ("exit".equals(saveOption)) {
                return "redirect:/business-plan/dashboard"; // Redirect to dashboard or home page
            } else {
                return "redirect:/business-plan/funding-request?planId=" + planId; // Redirect to the next section
            }
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Business plan not found.");
            return "redirect:/business-plan/create";
        }
    }


    @GetMapping("/funding-request")
   public String fundingRequestForm(@RequestParam("planId") Long planId, Model model, RedirectAttributes redirectAttributes) {
        Optional<BusinessPlan> optionalPlan = businessPlanService.findById(planId);
        if (optionalPlan.isPresent()) {
            model.addAttribute("businessPlan", optionalPlan.get());
            model.addAttribute("pageTitle", "Funding Request");
            return "business-plan/funding-request";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Business plan not found. Please create a new business plan.");
            return "redirect:/business-plan/create";
        }
   }
    @PostMapping("/funding-request")
    public String saveFundingRequest(
            @RequestParam("planId") Long planId,
            @RequestParam("fundingRequest") String fundingRequest,
            @RequestParam(value = "status", defaultValue = "IN_PROGRESS") String status,
            @RequestParam("saveOption") String saveOption, // "exit" or "continue"
            RedirectAttributes redirectAttributes) {

        Optional<BusinessPlan> optionalPlan = businessPlanService.findById(planId);
        if (optionalPlan.isPresent()) {
            BusinessPlan businessPlan = optionalPlan.get();
            businessPlan.setFundingRequest(fundingRequest);
            businessPlan.setStatus(status);
            businessPlanService.save(businessPlan);

            redirectAttributes.addFlashAttribute("successMessage", "Funding request saved successfully!");

            // Redirect based on save option
            if ("exit".equals(saveOption)) {
                return "redirect:/business-plan/dashboard"; // Redirect to dashboard or home page
            } else {
                return "redirect:/business-plan/review?planId=" + planId; // Redirect to the next section
            }
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Business plan not found.");
            return "redirect:/business-plan/create";
        }
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

    @GetMapping("/dashboard")
    public String viewDashboard(Model model) {
        List<BusinessPlan> businessPlans = businessPlanService.findAll(); // Assumes you have a findAll() method in the service
        model.addAttribute("businessPlans", businessPlans);
        return "business-plan/dashboard"; // Create a Thymeleaf template with this name
    }

}




