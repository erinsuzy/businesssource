package com.example.businesssource.controllers;

import com.example.businesssource.entities.*;
import com.example.businesssource.services.OrganizationManagementService;
import com.example.businesssource.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

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
    public String showOrganizationManagementForm(@RequestParam("planId") Long planId, Model model) {
        User currentUser = userService.getCurrentUser();
        BusinessPlan businessPlan = currentUser.getBusinessPlans()
                .stream()
                .filter(plan -> plan.getId().equals(planId))
                .findFirst()
                .orElse(null);

        if (businessPlan == null) {
            return "redirect:/business-plan/create"; // Redirect if no valid business plan is found
        }

        OrganizationManagement organizationManagement = organizationManagementService.getByBusinessPlan(businessPlan)
                .orElse(new OrganizationManagement());

        organizationManagement.setBusinessPlan(businessPlan);
        model.addAttribute("businessPlan", businessPlan);
        model.addAttribute("organizationManagement", organizationManagement);
        return "business-plan/organization-management";
    }


    @PostMapping
    public String saveOrganizationManagement(
            @RequestParam("planId") Long planId,
            @RequestParam("saveOption") String saveOption,
            @ModelAttribute OrganizationManagement organizationManagement,
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
        Optional<OrganizationManagement> existing = organizationManagementService.getByBusinessPlan(businessPlan);

        if (existing.isPresent()) {
            OrganizationManagement existingStructure = existing.get();
            existingStructure.setStructure(organizationManagement.getStructure());
            organizationManagement = existingStructure;
        }



        organizationManagement.setBusinessPlan(businessPlan);
        organizationManagementService.saveOrUpdate(organizationManagement);

        redirectAttributes.addFlashAttribute("successMessage", "Organization and management saved successfully!");

        if ("exit".equals(saveOption)) {
            return "redirect:/dashboard";
        } else {
            return "redirect:/business-plan/financial-projections?planId=" + planId;
        }
    }

}
