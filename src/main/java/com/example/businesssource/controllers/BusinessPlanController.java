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

    public BusinessPlanController(BusinessPlanService businessPlanService, UserService userService) {
        this.businessPlanService = businessPlanService;
        this.userService = userService;
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
