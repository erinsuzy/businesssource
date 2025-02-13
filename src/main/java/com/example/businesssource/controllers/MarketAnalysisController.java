package com.example.businesssource.controllers;

import com.example.businesssource.entities.BusinessPlan;
import com.example.businesssource.entities.MarketAnalysis;
import com.example.businesssource.entities.User;
import com.example.businesssource.repositories.MarketAnalysisRepository;
import com.example.businesssource.services.MarketAnalysisService;
import com.example.businesssource.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String showMarketAnalysisForm(Model model) {
        User currentUser = userService.getCurrentUser();
        BusinessPlan businessPlan = currentUser.getBusinessPlan();
        MarketAnalysis marketAnalysis = marketAnalysisService.getByBusinessPlan(businessPlan)
                .orElse(new MarketAnalysis());
        marketAnalysis.setBusinessPlan(businessPlan);
        model.addAttribute("marketAnalysis", marketAnalysis);
        return "/business-plan/market-analysis";
    }

    @PostMapping
    public String saveMarketAnalysis(Model model, @ModelAttribute MarketAnalysis marketAnalysis) {
        User currentUser = userService.getCurrentUser();
        BusinessPlan businessPlan = currentUser.getBusinessPlan();
        marketAnalysis.setBusinessPlan(businessPlan);
        marketAnalysisService.saveOrUpdate(marketAnalysis);
        return "redirect:/business-plan/organization-management";
    }
}
