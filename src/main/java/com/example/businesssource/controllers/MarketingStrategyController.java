package com.example.businesssource.controllers;

import com.example.businesssource.entities.*;
import com.example.businesssource.services.MarketingStrategyService;
import com.example.businesssource.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.yaml.snakeyaml.error.Mark;

import java.util.Optional;

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
    public String showMarketingStrategyForm(@RequestParam("planId") Long planId, Model model) {
        User currentUser = userService.getCurrentUser();
        BusinessPlan businessPlan = currentUser.getBusinessPlans()
                .stream()
                .filter(plan -> plan.getId().equals(planId))
                .findFirst()
                .orElse(null);

        if (businessPlan == null) {
            return "redirect:/business-plan/create"; // Redirect if no valid business plan is found
        }

        MarketingStrategy marketingStrategy = marketingStrategyService.getByBusinessPlan(businessPlan)
                .orElse(new MarketingStrategy());

        marketingStrategy.setBusinessPlan(businessPlan);
        model.addAttribute("businessPlan", businessPlan);
        model.addAttribute("marketingStrategy", marketingStrategy);
        return "business-plan/marketing-strategy";
    }


    @PostMapping
    public String saveMarketingStrategy(
            @RequestParam("planId") Long planId,
            @RequestParam("saveOption") String saveOption,
            @ModelAttribute MarketingStrategy marketingStrategy,
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
        Optional<MarketingStrategy> existing = marketingStrategyService.getByBusinessPlan(businessPlan);
        if (existing.isPresent()) {
            MarketingStrategy existingStrategy = existing.get();
            existingStrategy.setStrategy(marketingStrategy.getStrategy());
            marketingStrategy = existingStrategy;
        }
        marketingStrategy.setBusinessPlan(businessPlan);
        marketingStrategyService.saveOrUpdate(marketingStrategy);

        redirectAttributes.addFlashAttribute("successMessage", "Marketing strategy saved successfully!");

        if ("exit".equals(saveOption)) {
            return "redirect:/dashboard";
        } else {
            return "redirect:/business-plan/funding-request?planId=" + planId;
        }
    }

}