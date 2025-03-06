package com.example.businesssource.controllers;

import com.example.businesssource.entities.User;
import com.example.businesssource.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final UserService userService;

    public DashboardController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        try {
            User currentUser = userService.getCurrentUser();
            model.addAttribute("user", currentUser);
            model.addAttribute("businessPlans", currentUser.getBusinessPlans()); // Ensure business plans are available
            return "dashboard";  // ✅ Points to `dashboard.html`
        } catch (IllegalStateException e) {
            return "redirect:/login"; // Redirect if no user is logged in
        } catch (RuntimeException e) {
            model.addAttribute("error", "An unexpected error occurred. Please log in again.");
            return "error";
        }
    }
}
