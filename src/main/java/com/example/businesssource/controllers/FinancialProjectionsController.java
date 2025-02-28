package com.example.businesssource.controllers;

import com.example.businesssource.entities.BusinessPlan;
import com.example.businesssource.entities.CompanyDescription;
import com.example.businesssource.entities.FinancialProjections;
import com.example.businesssource.entities.User;
import com.example.businesssource.services.FinancialProjectionsService;
import com.example.businesssource.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String showFinancialProjectionsForm(Model model) {
        User currentUser = userService.getCurrentUser();
        BusinessPlan businessPlan = (BusinessPlan) currentUser.getBusinessPlans();
        FinancialProjections financialProjections = financialProjectionsService.getByBusinessPlan(businessPlan)
                .orElse(new FinancialProjections());

        financialProjections.setBusinessPlan(businessPlan); // Ensure linkage
        model.addAttribute("financialProjections", financialProjections);
        return "business-plan/financial-projections";
    }

    @PostMapping
    public String saveFinancialProjections(@ModelAttribute FinancialProjections financialProjections) {
        User currentUser = userService.getCurrentUser();
        BusinessPlan businessPlan = (BusinessPlan) currentUser.getBusinessPlans();
        financialProjections.setBusinessPlan(businessPlan);

        financialProjectionsService.saveOrUpdate(financialProjections);
        return "redirect:/business-plan/funding-request";
    }
}
