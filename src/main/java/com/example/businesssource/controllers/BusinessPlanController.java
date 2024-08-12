package com.example.businesssource.controllers;

import com.example.businesssource.entities.BusinessPlan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BusinessPlanController {

    @PostMapping("/business-plan/company-description")
    public String saveCompanyDescription(@ModelAttribute BusinessPlan businessPlan, Model model) {
        // Save the company description to the database or session
        model.addAttribute("businessPlan", businessPlan);
        return "redirect:/business-plan/market-analysis"; // redirect to the next section
    }
}
