package com.example.businesssource.controllers;

import com.example.businesssource.entities.*;
import com.example.businesssource.services.CompanyDescriptionService;
import com.example.businesssource.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/business-plan/company-description")
public class CompanyDescriptionController {
    private final CompanyDescriptionService companyDescriptionService;
    private final UserService userService;

    @Autowired
    public CompanyDescriptionController(CompanyDescriptionService companyDescriptionService, UserService userService) {
        this.companyDescriptionService = companyDescriptionService;
        this.userService = userService;
    }

    @GetMapping
    public String showCompanyDescriptionForm(@RequestParam("planId") Long planId, Model model) {
        User currentUser = userService.getCurrentUser();
        BusinessPlan businessPlan = currentUser.getBusinessPlans()
                .stream()
                .filter(plan -> plan.getId().equals(planId))
                .findFirst()
                .orElse(null);

        if (businessPlan == null) {
            return "redirect:/business-plan/create"; // Redirect if no valid business plan is found
        }

        CompanyDescription companyDescription = companyDescriptionService.getByBusinessPlan(businessPlan)
                .orElse(new CompanyDescription());

        companyDescription.setBusinessPlan(businessPlan);
        model.addAttribute("businessPlan", businessPlan);
        model.addAttribute("companyDescription", companyDescription);
        return "business-plan/company-description";
    }

   @PostMapping
    public String saveCompanyDescription(
            @RequestParam("planId") Long planId,
            @ModelAttribute CompanyDescription companyDescription,
            RedirectAttributes redirectAttributes) {

        User currentUser = userService.getCurrentUser();
        BusinessPlan businessPlan = currentUser.getBusinessPlans()
                .stream()
                .filter(plan -> plan.getId().equals(planId))
                .findFirst()
                .orElse(null);

        if (businessPlan == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Business plan not found.");
            return "redirect:/business-plan/create";
        }

        companyDescription.setBusinessPlan(businessPlan);
        companyDescriptionService.saveOrUpdate(companyDescription);

        redirectAttributes.addFlashAttribute("successMessage", "Company description saved successfully!");
        return "redirect:/business-plan/products-services?planId=" + planId;
    }

}
