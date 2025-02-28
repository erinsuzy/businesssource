package com.example.businesssource.controllers;

import com.example.businesssource.entities.BusinessPlan;
import com.example.businesssource.entities.MarketAnalysis;
import com.example.businesssource.entities.MarketingStrategy;
import com.example.businesssource.entities.User;
import com.example.businesssource.services.MarketingStrategyService;
import com.example.businesssource.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/business-plan/marketing-strategy")
public class MarketingStrategyController {

    private final MarketingStrategyService marketingStrategyService;

    private final UserService userService;


    public MarketingStrategyController(MarketingStrategyService marketingStrategyService, UserService userService) {
        this.marketingStrategyService = marketingStrategyService;
        this.userService = userService;
    }

    @GetMapping
    public String showMarketingStrategyForm(Model model) {
        User currentUser = userService.getCurrentUser();
        BusinessPlan businessPlan = (BusinessPlan) currentUser.getBusinessPlans();
        MarketingStrategy marketingStrategy = marketingStrategyService.getByBusinessPlan(businessPlan)
                .orElse(new MarketingStrategy());
        marketingStrategy.setBusinessPlan(businessPlan);
        model.addAttribute("marketingStrategy", marketingStrategy);
        return "/business-plan/marketing-strategy";
    }

    @PostMapping
    public String saveMarketingStrategy(Model model, @ModelAttribute MarketingStrategy marketingStrategy) {
        User currentUser = userService.getCurrentUser();
        BusinessPlan businessPlan = (BusinessPlan) currentUser.getBusinessPlans();
        marketingStrategy.setBusinessPlan(businessPlan);
        marketingStrategyService.saveOrUpdate(marketingStrategy);
        return "redirect:/business-plan/financial-projections";
    }
}