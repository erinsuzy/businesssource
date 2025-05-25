package com.example.businesssource.controllers;

import com.example.businesssource.entities.*;
import com.example.businesssource.services.FinancialProjectionsService;
import com.example.businesssource.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/business-plan/financial-projections")
public class FinancialProjectionsController {
    private final FinancialProjectionsService financialProjectionsService;
    private final UserService userService;

    public FinancialProjectionsController(FinancialProjectionsService financialProjectionsService, UserService userService) {
        this.financialProjectionsService = financialProjectionsService;
        this.userService = userService;
    }

    @GetMapping
    public String showFinancialProjectionsForm(@RequestParam("planId") Long planId, Model model) {
        User currentUser = userService.getCurrentUser();
        BusinessPlan businessPlan = currentUser.getBusinessPlans()
                .stream()
                .filter(plan -> plan.getId().equals(planId))
                .findFirst()
                .orElse(null);

        if (businessPlan == null) {
            return "redirect:/business-plan/create"; // Redirect if no valid business plan is found
        }

       FinancialProjections financialProjections = financialProjectionsService.getByBusinessPlan(businessPlan)
                .orElse(new FinancialProjections());

        financialProjections.setBusinessPlan(businessPlan);
        model.addAttribute("businessPlan", businessPlan);
        model.addAttribute("financialProjections", financialProjections);
        return "business-plan/financial-projections";
    }

    @PostMapping
    public String saveFinancialProjections(
            @RequestParam("planId") Long planId,
            @RequestParam("saveOption") String saveOption,
            @ModelAttribute FinancialProjections financialProjections,
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
        Optional<FinancialProjections> existing = financialProjectionsService.getByBusinessPlan(businessPlan);

        if (existing.isPresent()) {
            FinancialProjections existingProjections = existing.get();
            existingProjections.setForecast(financialProjections.getForecast());
            financialProjections = existingProjections;
        }

        financialProjections.setBusinessPlan(businessPlan);
        financialProjectionsService.saveOrUpdate(financialProjections);

        redirectAttributes.addFlashAttribute("successMessage", "Financial projections saved successfully!");

        if ("exit".equals(saveOption)) {
            return "redirect:/dashboard";
        } else {
            return "redirect:/business-plan/marketing-strategy?planId=" + planId;
        }
    }


}
