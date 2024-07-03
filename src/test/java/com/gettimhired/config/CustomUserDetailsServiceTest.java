package com.gettimhired.config;

import com.gettimhired.model.mongo.User;
import com.gettimhired.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

class CustomUserDetailsServiceTest {

    private CustomUserDetailsService customUserDetailsService;
    private UserService userService;

    @BeforeEach
    public void init() {
        userService = Mockito.mock(UserService.class);
        customUserDetailsService = new CustomUserDetailsService(userService);
    }

    @Test
    public void testUserDetailsServiceHappy() {
        var userOpt = Optional.of(new User("BARK_ID", "BARK_PASSWORD"));
        Mockito.when(userService.findUserByUsername("BARK")).thenReturn(userOpt);

        var userDetails = customUserDetailsService.loadUserByUsername("BARK");

        Mockito.verify(userService, Mockito.times(1)).findUserByUsername("BARK");
        Assertions.assertEquals("BARK_ID", userDetails.getUsername());
        Assertions.assertEquals("BARK_PASSWORD", userDetails.getPassword());
        Assertions.assertNotNull(userDetails.getAuthorities());
        Assertions.assertEquals(1, userDetails.getAuthorities().size());
    }

    @Test
    public void testUserDetailsServiceUserNotFound() {
        Optional<User> userOpt = Optional.empty();
        Mockito.when(userService.findUserByUsername("BARK")).thenReturn(userOpt);

        Assertions.assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserByUsername("BARK"));

        Mockito.verify(userService, Mockito.times(1)).findUserByUsername("BARK");
    }

}