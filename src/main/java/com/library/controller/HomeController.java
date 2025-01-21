package com.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @GetMapping("/")
    public String homePage(@RequestParam(value = "sortBy", required = false) String sortBy, Model model) {
        model.addAttribute("sortBy", sortBy);
        return "index";
    }
}