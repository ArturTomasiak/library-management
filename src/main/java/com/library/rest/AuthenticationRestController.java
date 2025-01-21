package com.library.rest;

import com.library.entity.Profile;
import com.library.entity.User;
import com.library.service.ProfileService;
import com.library.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication and account management")
public class AuthenticationRestController {

    private static final Logger logger = LoggerFactory.getLogger(LoanRestController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    @Operation(summary = "Register a new user", description = "Creates a new user account with profile information")
    public ResponseEntity<Void> signup(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam(required = false) String phoneNumber,
            HttpServletRequest request) {

        if (userService.findUserByUsername(username) != null || userService.existsByEmail(email)) {
            logger.warn("User already exists");
            return ResponseEntity.badRequest().build();
        }

        Profile profile = new Profile();
        profile.setFirstName(firstName);
        profile.setLastName(lastName);
        profile.setPhoneNumber(phoneNumber);
        Profile savedProfile = profileService.saveProfile(profile);

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setProfile(savedProfile);
        userService.saveUser(user);

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(authToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

        logger.info("new user created; authentication successful");
        return ResponseEntity.status(302).header("Location", "/").build();
    }


    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticates a user with username and password")
    public ResponseEntity<String> login(
            @RequestParam String username,
            @RequestParam String password) {

        User user = userService.findUserByUsername(username);

        if (user == null || !passwordEncoder.matches(password, user.getPasswordHash())) {
            logger.warn("User not found");
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        logger.info("Login successful");
        return ResponseEntity.ok("Login successful");
    }
}
