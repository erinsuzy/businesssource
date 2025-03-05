package com.example.businesssource.controllers;

import com.example.businesssource.entities.BusinessPlan;
import com.example.businesssource.entities.MarketAnalysis;
import com.example.businesssource.entities.User;
import com.example.businesssource.repositories.MarketAnalysisRepository;
import com.example.businesssource.services.MarketAnalysisService;
import com.example.businesssource.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/business-plan/market-analysis")
public class MarketAnalysisController {

    private final MarketAnalysisService marketAnalysisService;
    private final UserService userService;

    public MarketAnalysisController(MarketAnalysisService marketAnalysisService, UserService userService) {
        this.marketAnalysisService = marketAnalysisService;
        this.userService = userService;
    }

    @GetMapping
    public String showMarketAnalysisForm(@RequestParam("planId") Long planId, Model model) {
        User currentUser = userService.getCurrentUser();
        BusinessPlan businessPlan = currentUser.getBusinessPlans()
                .stream()
                .filter(plan -> plan.getId().equals(planId))
                .findFirst()
                .orElse(null);

        if (businessPlan == null) {
            return "redirect:/business-plan/create"; // Redirect if no valid business plan is found
        }

        MarketAnalysis marketAnalysis = marketAnalysisService.getByBusinessPlan(businessPlan)
                .orElse(new MarketAnalysis());

        marketAnalysis.setBusinessPlan(businessPlan);
        model.addAttribute("businessPlan", businessPlan);
        model.addAttribute("marketAnalysis", marketAnalysis);
        return "business-plan/market-analysis"; // Ensure this matches your Thymeleaf template path
    }

    @PostMapping
    public String saveMarketAnalysis(
            @RequestParam("planId") Long planId,
            @ModelAttribute MarketAnalysis marketAnalysis,
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

        marketAnalysis.setBusinessPlan(businessPlan);
        marketAnalysisService.saveOrUpdate(marketAnalysis);

        redirectAttributes.addFlashAttribute("successMessage", "Market analysis saved successfully!");
        return "redirect:/business-plan/organization-management?planId=" + planId;
    }
}
