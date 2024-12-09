package com.example.businesssource.controllers;

import com.example.businesssource.entities.User;
import com.example.businesssource.exceptions.UserAlreadyExistsException;
import com.example.businesssource.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SignupController {

    @Autowired
    private UserService userService;

    @GetMapping("/signup")
    public String showSignupPage() {
        return "signup"; // Refers to templates/signup.html
    }

    @PostMapping("/signup")
    public String handleSignup(@RequestParam String username, @RequestParam String email, @RequestParam String password, HttpSession session) {
        userService.createUser(username, email, password);

        // Check if quiz data exists in session
        Boolean quizCompleted = (Boolean) session.getAttribute("quizCompleted");
        String quizResult = (String) session.getAttribute("quizResult");

        if (quizCompleted != null && quizCompleted) {
            // Save quiz completion status and result to the user's account
            User user = userService.getUserByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
            user.setHasCompletedQuiz(true);
            user.setQuizResult(quizResult);
            userService.save(user);
        }

        session.invalidate(); // Clear session data to avoid duplication
        return "redirect:/login?signupSuccess=true";
    }



}


