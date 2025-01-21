package com.library.rest;

import com.library.entity.Profile;
import com.library.entity.User;
import com.library.service.ProfileService;
import com.library.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationRestControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private ProfileService profileService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthenticationRestController authController;

    private User user;
    private Profile profile;

    @BeforeEach
    void setUp() {
        profile = new Profile();
        profile.setProfileId(1L);
        profile.setFirstName("John");

        user = new User();
        user.setUserId(1);
        user.setUsername("testUser");
        user.setEmail("test@example.com");
        user.setProfile(profile);
    }

    @Test
    void testSignup_Failure_UsernameExists() {
        when(userService.findUserByUsername("testUser")).thenReturn(user);

        ResponseEntity<Void> response = authController.signup("testUser", "test@example.com", "password", "John", "Doe", null, null);

        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    void testSignup_Success() {
        when(userService.findUserByUsername("testUser")).thenReturn(null);
        when(userService.existsByEmail("test@example.com")).thenReturn(false);
        when(passwordEncoder.encode("password")).thenReturn("hashed_password");

        ResponseEntity<Void> response = authController.signup("testUser", "test@example.com", "password", "John", "Doe", null, null);

        assertEquals(302, response.getStatusCode().value());
    }
}
