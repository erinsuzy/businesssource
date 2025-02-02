package com.example.businesssource.controllers;

import com.example.businesssource.entities.BusinessPlan;
import com.example.businesssource.entities.User;
import com.example.businesssource.services.BusinessPlanService;
import com.example.businesssource.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/business-plan")
public class BusinessPlanController {

    private final BusinessPlanService businessPlanService;

    private final UserService userService;

    @Autowired
    public BusinessPlanController(BusinessPlanService businessPlanService, UserService userService) {
        this.businessPlanService = businessPlanService;
        this.userService = userService;
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
    public String showDashboard(Model model) {
        try {
            User currentUser = userService.getCurrentUser();
            model.addAttribute("user", currentUser);
            return "dashboard";
        } catch (IllegalStateException e) {
            return "redirect:/login"; // Redirect if no user is logged in
        } catch (RuntimeException e) {
            model.addAttribute("error", "An unexpected error occurred. Please log in again.");
            return "error";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteBusinessPlan(@PathVariable Long id) {
        businessPlanService.deleteBusinessPlan(id);
        return "redirect:/business-plan/dashboard";
    }

    @GetMapping("/export")
    public void exportBusinessPlanToPdf(@RequestParam Long planId, HttpServletResponse response) throws IOException {
        BusinessPlan businessPlan = businessPlanService.findBusinessPlanById(planId);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=business_plan.pdf");
        OutputStream outputStream = response.getOutputStream();
        businessPlanService.generatePdf(businessPlan, outputStream);
        outputStream.close();
    }




    @GetMapping("/resume/{id}")
    public String resumeBusinessPlan(@PathVariable Long id, Model model) {
        BusinessPlan existingPlan = businessPlanService.findBusinessPlanById(id);
        if (existingPlan != null) {
            model.addAttribute("businessPlan", existingPlan);
            return "business-plan/review"; // Make sure this points to your edit view
        } else {
            // Handle case where the plan doesn't exist
            model.addAttribute("error", "Business plan not found.");
            return "redirect:/dashboard";
        }
    }
    @PostMapping("/mark-completed")
    public String markAsCompleted(@RequestParam Long planId, RedirectAttributes redirectAttributes) {
        Optional<BusinessPlan> optionalPlan = businessPlanService.findById(planId);

        if (optionalPlan.isPresent()) {
            BusinessPlan businessPlan = optionalPlan.get();
            businessPlan.setStatus("COMPLETED");
            businessPlanService.save(businessPlan); // Save the updated status

            redirectAttributes.addFlashAttribute("successMessage", "Business plan marked as completed!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Business plan not found.");
        }

        return "redirect:/business-plan/review?planId=" + planId;
    }

}




