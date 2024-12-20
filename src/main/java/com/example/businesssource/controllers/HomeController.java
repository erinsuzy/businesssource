package com.example.businesssource.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Home Page - No longer enforces quiz completion
    @GetMapping("/")
    public String handleHomePage() {
        return "home"; // Always render home.html
    }

    // Results Page - Checks if quiz result is available, otherwise displays an appropriate message
    @GetMapping("/results")
    public String showResultsPage(HttpSession session, Model model) {
        String result = (String) session.getAttribute("result");

        if (result != null) {
            model.addAttribute("result", result); // Pass result to the view
            return "results"; // Render results.html
        } else {
            model.addAttribute("error", "No quiz results found. Please take the quiz to see your results.");
            return "results"; // Render results.html with error message
        }
    }

    // Quiz Page
    @GetMapping("/quiz")
    public String showQuizPage() {
        return "questions"; // Render questions.html for the quiz
    }
}




