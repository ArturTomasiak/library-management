package com.library.rest;

import com.library.entity.User;
import com.library.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/profile")
@Tag(name = "User Profile", description = "Endpoints for managing user profiles")
public class ProfileRestController {

    private static final Logger logger = LoggerFactory.getLogger(LoanRestController.class);

    private final UserService userService;

    public ProfileRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Get user profile", description = "Retrieves the profile information of the authenticated user")
    public User getProfile(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            logger.error("Authentication required");
            throw new RuntimeException("Unauthorized access");
        }

        String username = authentication.getName();
        User user = userService.findUserByUsername(username);

        if (user == null) {
            logger.error("User not found");
            throw new RuntimeException("User not found");
        }

        return user;
    }
}
