package com.example.businesssource.controllers;

import com.example.businesssource.entities.BusinessPlan;
import com.example.businesssource.entities.User;
import com.example.businesssource.services.BusinessPlanService;
import com.example.businesssource.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.OutputStream;
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

    // Create a new business plan
    @GetMapping("/create")
    public String createBusinessPlan(Model model) {
        model.addAttribute("businessPlan", new BusinessPlan());
        return "business-plan/create";
    }

    @PostMapping("/create")
    public String saveBusinessPlan(@ModelAttribute BusinessPlan businessPlan, RedirectAttributes redirectAttributes) {
        businessPlan.setStatus("DRAFT");
        BusinessPlan savedPlan = businessPlanService.save(businessPlan);
        redirectAttributes.addFlashAttribute("message", "Progress saved! You can return to this plan anytime.");
        return "redirect:/business-plan/company-description?planId=" + savedPlan.getId();
    }

    // Review full business plan
    @GetMapping("/review")
    public String reviewBusinessPlan(@RequestParam("planId") Long planId, Model model, RedirectAttributes redirectAttributes) {
        Optional<BusinessPlan> optionalPlan = businessPlanService.findById(planId);
        if (optionalPlan.isPresent()) {
            model.addAttribute("businessPlan", optionalPlan.get());
            return "business-plan/review";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Business plan not found.");
            return "redirect:/business-plan/create";
        }
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

    // Show dashboard with business plans
    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        try {
            User currentUser = userService.getCurrentUser();
            model.addAttribute("user", currentUser);
            return "dashboard";
        } catch (IllegalStateException e) {
            return "redirect:/login";
        } catch (RuntimeException e) {
            model.addAttribute("error", "An unexpected error occurred. Please log in again.");
            return "error";
        }
    }

    // Delete a business plan
    @PostMapping("/delete/{id}")
    public String deleteBusinessPlan(@PathVariable Long id) {
        businessPlanService.deleteBusinessPlan(id);
        return "redirect:/business-plan/dashboard";
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

    // Resume a business plan
    @GetMapping("/resume/{id}")
    public String resumeBusinessPlan(@PathVariable Long id, Model model) {
        BusinessPlan existingPlan = businessPlanService.findBusinessPlanById(id);
        if (existingPlan != null) {
            model.addAttribute("businessPlan", existingPlan);
            return "business-plan/review";
        } else {
            model.addAttribute("error", "Business plan not found.");
            return "redirect:/dashboard";
        }
    }
}
