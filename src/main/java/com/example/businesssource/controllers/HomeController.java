package com.example.businesssource.controllers;

import com.example.businesssource.entities.User;
import com.example.businesssource.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String handleHomePage(HttpSession session) {
        Boolean hasCompletedQuiz = (Boolean) session.getAttribute("quizCompleted");
        if (hasCompletedQuiz == null || !hasCompletedQuiz) {
            return "redirect:/quiz"; // Redirect to quiz if not completed
        }
        return "home"; // Render home.html
    }

    @GetMapping("/quiz/results")
    public String showResultsPage(HttpSession session, Model model) {
        Boolean quizCompleted = (Boolean) session.getAttribute("quizCompleted");
        String quizResult = (String) session.getAttribute("quizResult");

        if (quizCompleted != null && quizCompleted) {
            model.addAttribute("quizResult", quizResult);
            return "results"; // Show results page
        } else {
            return "redirect:/quiz"; // Redirect to quiz if not completed
        }
    }


    @GetMapping("/quiz")
    public String showQuizPage() {
        return "questions"; // Render the template
    }
}




