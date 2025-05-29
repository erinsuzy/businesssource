package com.example.businesssource.controllers;

import com.example.businesssource.entities.User;
import com.example.businesssource.exceptions.UserAlreadyExistsException;
import com.example.businesssource.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder; // Add password encoder

    @Autowired
    public UserController(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // Show the login page
    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // Points to login.html
    }

    // Show the signup page
    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new User()); // Add user object for the signup form
        return "signup"; // Points to signup.html
    }

    @PostMapping("/signup")
    public String processSignUp(@ModelAttribute("user") User user, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "signup";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        try {
            userService.saveUser(user);
        } catch (UserAlreadyExistsException e) {
            model.addAttribute("formError", e.getMessage()); // <-- Add this line
            return "signup";
        }

        redirectAttributes.addFlashAttribute("success", "Account created successfully! Please log in.");
        return "redirect:/login";
    }

}


