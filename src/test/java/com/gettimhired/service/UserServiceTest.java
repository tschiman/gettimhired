package com.gettimhired.service;

import com.gettimhired.TestHelper;
import com.gettimhired.model.mongo.User;
import com.gettimhired.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserService userService;

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    public void init() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    public void testCreateUserHappy() {
        Mockito.when(userRepository.save(any())).thenReturn(new User("BARK_USER", "BARK_PASSWORD"));

        User user = userService.createUser();

        assertNotNull(user.id());
        assertNotNull(user.password());
        Mockito.verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testFindUserByUsername() {
        when(userRepository.findById("BARK")).thenReturn(Optional.of(new User(TestHelper.ID, "BARK_PASSWORD")));

        var userOpt = userService.findUserByUsername("BARK");

        verify(userRepository, times(1)).findById("BARK");
        assertTrue(userOpt.isPresent());
    }

}