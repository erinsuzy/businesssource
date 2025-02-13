package com.example.businesssource.controllers;

import com.example.businesssource.entities.BusinessPlan;
import com.example.businesssource.entities.OrganizationManagement;
import com.example.businesssource.entities.User;
import com.example.businesssource.services.OrganizationManagementService;
import com.example.businesssource.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/business-plan/organization-management")
public class OrganizationManagementController {

    private final OrganizationManagementService organizationManagementService;

    private final UserService userService;

    public OrganizationManagementController(OrganizationManagementService organizationManagementService, UserService userService) {
        this.organizationManagementService = organizationManagementService;
        this.userService = userService;
    }


    @GetMapping
    public String showOrganizationManagementForm(Model model) {
        User currentUser = userService.getCurrentUser();
        BusinessPlan businessPlan = currentUser.getBusinessPlan();
        OrganizationManagement organizationManagement = organizationManagementService.getByBusinessPlan(businessPlan)
                .orElse(new OrganizationManagement());
        organizationManagement.setBusinessPlan(businessPlan);
        model.addAttribute("organizationManagement", organizationManagement);
        return "/business-plan/organization-management";
    }

    @PostMapping
    public String saveOrganizationManagement(Model model, @ModelAttribute OrganizationManagement organizationManagement) {
        User currentUser = userService.getCurrentUser();
        BusinessPlan businessPlan = currentUser.getBusinessPlan();
        organizationManagement.setBusinessPlan(businessPlan);
        organizationManagementService.saveOrUpdate(organizationManagement);
        return "redirect:/business-plan/products-services";
    }
}
