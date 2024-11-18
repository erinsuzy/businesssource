package com.example.businesssource.controllers;

import com.example.businesssource.entities.User;
import com.example.businesssource.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {
    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

  /*  @GetMapping("/")
        public String handleHomePage(HttpSession session) {

        User user = userService.getCurrentUser(session);
            if (user != null) {
                if (!user.isHasCompletedQuiz()) {
                    return "redirect:/quiz";
                }
                return "redirect:/dashboard";
            }
            return "redirect:/login";
        }

        @PostMapping("/quiz/complete")
        public String completeQuiz(HttpSession session) {
            User user = userService.getCurrentUser(session);
            if (user != null) {
                user.setHasCompletedQuiz(true);
                userService.save(user);
            }
            return "redirect:/dashboard";
        }*/



        @GetMapping("/quiz")
        public String showQuizPage() {
            return "quiz/questions"; // Points to Thymeleaf template quiz.html
        }

        @GetMapping("/")
        public String redirectToQuiz() {
            return "redirect:/quiz";
        }
    }



