package com.example.businesssource.controllers;

import com.example.businesssource.entities.*;
import com.example.businesssource.services.ProductsServicesService;
import com.example.businesssource.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/business-plan/products-services")
public class ProductsServicesController {

    private final ProductsServicesService productsServicesService;

    private final UserService userService;


    public ProductsServicesController(ProductsServicesService productsServicesService, UserService userService) {
        this.productsServicesService = productsServicesService;
        this.userService = userService;
    }

    @GetMapping
    public String showProductsServicesForm(@RequestParam("planId") Long planId, Model model) {
        User currentUser = userService.getCurrentUser();
        BusinessPlan businessPlan = currentUser.getBusinessPlans()
                .stream()
                .filter(plan -> plan.getId().equals(planId))
                .findFirst()
                .orElse(null);

        if (businessPlan == null) {
            return "redirect:/business-plan/create"; // Redirect if no valid business plan is found
        }

        ProductsServices productsServices = productsServicesService.getByBusinessPlan(businessPlan)
                .orElse(new ProductsServices());

        productsServices.setBusinessPlan(businessPlan);
        model.addAttribute("businessPlan", businessPlan);
        model.addAttribute("productsServices", productsServices);
        return "business-plan/products-services"; // Ensure this matches your Thymeleaf template path
    }

    @PostMapping
    public String saveProductsServices(
            @RequestParam("planId") Long planId,
            @RequestParam("saveOption") String saveOption,
            @ModelAttribute ProductsServices productsServices,
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
        Optional<ProductsServices> existing = productsServicesService.getByBusinessPlan(businessPlan);

        if (existing.isPresent()) {
           ProductsServices existingDescription = existing.get();
            existingDescription.setDescription(productsServices.getDescription());
            productsServices = existingDescription;
        }

        productsServices.setBusinessPlan(businessPlan);
        productsServicesService.saveOrUpdate(productsServices);

        redirectAttributes.addFlashAttribute("successMessage", "Products and services saved successfully!");

        if ("exit".equals(saveOption)) {
            return "redirect:/dashboard";
        } else {
            return "redirect:/business-plan/market-analysis?planId=" + planId;
        }
    }

}
