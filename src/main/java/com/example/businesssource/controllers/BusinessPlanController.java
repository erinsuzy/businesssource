package com.example.businesssource.controllers;

import com.example.businesssource.entities.BusinessPlan;
import com.example.businesssource.entities.User;
import com.example.businesssource.services.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/business-plan")
public class BusinessPlanController {

    private static final Logger logger = LoggerFactory.getLogger(BusinessPlanController.class);

    private final BusinessPlanService businessPlanService;
    private final UserService userService;
    private final CompanyDescriptionService companyDescriptionService;
    private final MarketAnalysisService marketAnalysisService;
    private final OrganizationManagementService organizationManagementService;
    private final ProductsServicesService productsServicesService;
    private final MarketingStrategyService marketingStrategyService;
    private final FinancialProjectionsService financialProjectionsService;
    private final FundingRequestService fundingRequestService;

    public BusinessPlanController(BusinessPlanService businessPlanService, UserService userService, CompanyDescriptionService companyDescriptionService, MarketAnalysisService marketAnalysisService, OrganizationManagementService organizationManagementService, ProductsServicesService productsServicesService, MarketingStrategyService marketingStrategyService, FinancialProjectionsService financialProjectionsService, FundingRequestService fundingRequestService) {
        this.businessPlanService = businessPlanService;
        this.userService = userService;
        this.companyDescriptionService = companyDescriptionService;
        this.marketAnalysisService = marketAnalysisService;
        this.organizationManagementService = organizationManagementService;
        this.productsServicesService = productsServicesService;
        this.marketingStrategyService = marketingStrategyService;
        this.financialProjectionsService = financialProjectionsService;
        this.fundingRequestService = fundingRequestService;
    }

    @GetMapping("/create")
    public String createBusinessPlan(Model model) {
        logger.info("🟢 GET /business-plan/create endpoint hit."); // ✅ Log when this method runs
        BusinessPlan businessPlan = new BusinessPlan();
        model.addAttribute("businessPlan", businessPlan);
        logger.info("🟢 BusinessPlan object added to model: {}", businessPlan);
        return "business-plan/create"; // ✅ Must match Thymeleaf template name
    }



    @PostMapping("/create")
    public String saveBusinessPlan(@ModelAttribute BusinessPlan businessPlan,
                                   @RequestParam("action") String action,
                                   RedirectAttributes redirectAttributes) {
        User currentUser = userService.getCurrentUser(); // Get logged-in user
        businessPlan.setUser(currentUser);               // ✅ Set the user

        businessPlan.setStatus("DRAFT");
        BusinessPlan savedPlan = businessPlanService.save(businessPlan);

        redirectAttributes.addFlashAttribute("message", "Progress saved!");

        if ("exit".equals(action)) {
            return "redirect:/dashboard";
        }
        return "redirect:/business-plan/company-description?planId=" + savedPlan.getId();
    }

    @GetMapping("/review")
    public String reviewBusinessPlan(@RequestParam("planId") Long planId, Model model, RedirectAttributes redirectAttributes) {
        Optional<BusinessPlan> optionalPlan = businessPlanService.findById(planId);
        if (optionalPlan.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Business plan not found.");
            return "redirect:/dashboard";
        }

        BusinessPlan plan = optionalPlan.get();
        model.addAttribute("businessPlan", plan);
        System.out.println(companyDescriptionService.getByBusinessPlan(plan).orElse(null));


        model.addAttribute("companyDescription", companyDescriptionService.getByBusinessPlan(plan).orElse(null));
        model.addAttribute("marketAnalysis", marketAnalysisService.getByBusinessPlan(plan).orElse(null));
        model.addAttribute("organizationManagement", organizationManagementService.getByBusinessPlan(plan).orElse(null));
        model.addAttribute("productsServices", productsServicesService.getByBusinessPlan(plan).orElse(null));
        model.addAttribute("marketingStrategy", marketingStrategyService.getByBusinessPlan(plan).orElse(null));
        model.addAttribute("financialProjections", financialProjectionsService.getByBusinessPlan(plan).orElse(null));
        model.addAttribute("fundingRequest", fundingRequestService.getByBusinessPlan(plan).orElse(null));

        return "business-plan/review";
    }


    // Mark the business plan as completed
    @PostMapping("/mark-completed")
    public String markAsCompleted(@RequestParam Long planId, RedirectAttributes redirectAttributes) {
        Optional<BusinessPlan> optionalPlan = businessPlanService.findById(planId);
        if (optionalPlan.isPresent()) {
            BusinessPlan businessPlan = optionalPlan.get();
            businessPlan.setStatus("COMPLETED");
            businessPlanService.save(businessPlan);
            redirectAttributes.addFlashAttribute("successMessage", "Business plan marked as completed!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Business plan not found.");
        }
        return "redirect:/business-plan/review?planId=" + planId;
    }

    // Delete a business plan
    @PostMapping("/delete/{id}")
    public String deleteBusinessPlan(@PathVariable Long id) {
        businessPlanService.deleteBusinessPlan(id);
        return "redirect:/dashboard";
    }

    // Export business plan as a PDF
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
    public String resumeBusinessPlan(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<BusinessPlan> optionalPlan = businessPlanService.findById(id);
        if (optionalPlan.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Business plan not found.");
            return "redirect:/dashboard";
        }

        BusinessPlan existingPlan = optionalPlan.get();
        model.addAttribute("businessPlan", existingPlan);

        // ✅ Add each section to the model just like /review
        model.addAttribute("companyDescription", companyDescriptionService.getByBusinessPlan(existingPlan).orElse(null));
        model.addAttribute("marketAnalysis", marketAnalysisService.getByBusinessPlan(existingPlan).orElse(null));
        model.addAttribute("organizationManagement", organizationManagementService.getByBusinessPlan(existingPlan).orElse(null));
        model.addAttribute("productsServices", productsServicesService.getByBusinessPlan(existingPlan).orElse(null));
        model.addAttribute("marketingStrategy", marketingStrategyService.getByBusinessPlan(existingPlan).orElse(null));
        model.addAttribute("financialProjections", financialProjectionsService.getByBusinessPlan(existingPlan).orElse(null));
        model.addAttribute("fundingRequest", fundingRequestService.getByBusinessPlan(existingPlan).orElse(null));

        return "business-plan/review";
    }

}
