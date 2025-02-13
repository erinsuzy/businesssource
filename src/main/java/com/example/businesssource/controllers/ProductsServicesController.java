package com.example.businesssource.controllers;

import com.example.businesssource.entities.BusinessPlan;
import com.example.businesssource.entities.ProductsServices;
import com.example.businesssource.entities.User;
import com.example.businesssource.services.ProductsServicesService;
import com.example.businesssource.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String showProductsServicesForm(Model model) {
        User currentUser = userService.getCurrentUser();
        BusinessPlan businessPlan = currentUser.getBusinessPlan();
        ProductsServices productsServices = productsServicesService.getByBusinessPlan(businessPlan)
                .orElse(new ProductsServices());
        productsServices.setBusinessPlan(businessPlan);
        model.addAttribute("productsServices", productsServices);
        return "business-plan/products-services";
    }

    @PostMapping
    public String saveProductsServices(Model model, @ModelAttribute  ProductsServices productsServices) {
        User currentUser = userService.getCurrentUser();
        BusinessPlan businessPlan = currentUser.getBusinessPlan();
        productsServices.setBusinessPlan(businessPlan);
        productsServicesService.saveOrUpdate(productsServices);
        return "redirect:/business-plan/marketing-strategy";
    }

}
