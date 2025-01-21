package com.library.service;

import com.library.entity.Profile;
import com.library.repository.ProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

    @Mock
    private ProfileRepository profileRepository;

    @InjectMocks
    private ProfileService profileService;

    private Profile profile;

    @BeforeEach
    void setUp() {
        profile = new Profile();
        profile.setProfileId(1L);
        profile.setFirstName("John");
        profile.setLastName("Doe");
    }

    @Test
    void testSaveProfile() {
        when(profileRepository.save(profile)).thenReturn(profile);

        Profile savedProfile = profileService.saveProfile(profile);

        assertNotNull(savedProfile);
        assertEquals("John", savedProfile.getFirstName());
        verify(profileRepository, times(1)).save(profile);
    }
}
