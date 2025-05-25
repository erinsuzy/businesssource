package com.example.businesssource.controllers;

import com.example.businesssource.entities.*;
import com.example.businesssource.services.FundingRequestService;
import com.example.businesssource.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/business-plan/funding-request")
public class FundingRequestController {

    private final FundingRequestService fundingRequestService;

    private final UserService userService;

    public FundingRequestController(FundingRequestService fundingRequestService, UserService userService) {
        this.fundingRequestService = fundingRequestService;
        this.userService = userService;
    }

    @GetMapping
    public String showFundingRequestForm(@RequestParam("planId") Long planId, Model model) {
        User currentUser = userService.getCurrentUser();
        BusinessPlan businessPlan = currentUser.getBusinessPlans()
                .stream()
                .filter(plan -> plan.getId().equals(planId))
                .findFirst()
                .orElse(null);

        if (businessPlan == null) {
            return "redirect:/business-plan/create"; // Redirect if no valid business plan is found
        }

        FundingRequest fundingRequest = fundingRequestService.getByBusinessPlan(businessPlan)
                .orElse(new FundingRequest());

        fundingRequest.setBusinessPlan(businessPlan);
        model.addAttribute("businessPlan", businessPlan);
        model.addAttribute("fundingRequest", fundingRequest);
        return "business-plan/funding-request";
    }

    @PostMapping
    public String saveFundingRequest(
            @RequestParam("planId") Long planId,
            @RequestParam("saveOption") String saveOption,
            @ModelAttribute FundingRequest fundingRequest,
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
        // Check if a Funding Request already exists for this Business Plan
        Optional<FundingRequest> existing = fundingRequestService.getByBusinessPlan(businessPlan);

        if (existing.isPresent()) {
            FundingRequest existingFunding = existing.get();
            existingFunding.setFunds(fundingRequest.getFunds());
            fundingRequest = existingFunding;
        }

        fundingRequest.setBusinessPlan(businessPlan);
        fundingRequestService.saveOrUpdate(fundingRequest);
        redirectAttributes.addFlashAttribute("successMessage", "Funding request saved successfully!");

        if ("exit".equals(saveOption)) {
            return "redirect:/dashboard";
        } else {
            return "redirect:/business-plan/review?planId=" + planId;
        }
    }

}
