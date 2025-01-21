package com.library.controller;

import com.library.entity.Loan;
import com.library.entity.User;
import com.library.service.UserService;
import com.library.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProfileController {
    private final UserService userService;
    private final LoanService loanService;

    @Autowired
    public ProfileController(UserService userService, LoanService loanService) {
        this.userService = userService;
        this.loanService = loanService;
    }

    @GetMapping("/profile")
    public String showProfile(Authentication authentication, Model model) {
        String username = authentication.getName();
        User user = userService.findUserByUsername(username);
        List<Loan> loans = loanService.getLoansByUserId(user.getUserId());
        model.addAttribute("user", user);
        model.addAttribute("loans", loans);
        return "profile";
    }

    @PostMapping("/profile/returnBook")
    public String returnBook(@RequestParam("loanId") Integer loanId) {
        loanService.returnBook(loanId);
        return "redirect:/profile";
    }
}
