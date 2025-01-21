package com.library.rest;

import com.library.entity.User;
import com.library.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "Endpoints for user management")
public class UserRestController {

    private static final Logger logger = LoggerFactory.getLogger(LoanRestController.class);

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Create a new user", description = "Saves a new user to the database")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        logger.info("user created: " + savedUser.getUsername());
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/{username}")
    @Operation(summary = "Get user by username", description = "Retrieves user details using username")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.findUserByUsername(username);
        logger.info("found user: " + username);
        return ResponseEntity.ok(user);
    }
}
