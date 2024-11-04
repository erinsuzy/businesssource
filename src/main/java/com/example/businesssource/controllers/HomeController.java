package com.example.businesssource.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/") // Maps the root URL to the home page
    public String home() {
        return "home"; // Returns the "home.html" template from the templates folder
    }
}
