package com.example.businesssource.controllers;

import com.example.businesssource.entities.BusinessPlan;
import com.example.businesssource.entities.CompanyDescription;
import com.example.businesssource.entities.User;
import com.example.businesssource.services.CompanyDescriptionService;
import com.example.businesssource.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String showCompanyDescriptionForm(Model model) {
        User currentUser = userService.getCurrentUser();
        BusinessPlan businessPlan = currentUser.getBusinessPlan();
        CompanyDescription companyDescription = companyDescriptionService.getByBusinessPlan(businessPlan)
                .orElse(new CompanyDescription());

        companyDescription.setBusinessPlan(businessPlan); // Ensure linkage
        model.addAttribute("companyDescription", companyDescription);
        return "business-plan/company-description";
    }


    @PostMapping
    public String saveCompanyDescription(@ModelAttribute CompanyDescription companyDescription) {
        User currentUser = userService.getCurrentUser();
        BusinessPlan businessPlan = currentUser.getBusinessPlan();
        companyDescription.setBusinessPlan(businessPlan);

        companyDescriptionService.saveOrUpdate(companyDescription);
        return "redirect:/business-plan/market-analysis";
    }

}
