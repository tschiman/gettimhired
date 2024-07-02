package com.gettimhired.service;

import com.gettimhired.model.mongo.User;
import com.gettimhired.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

class UserServiceTest {

    private UserService userService;

    private UserRepository userRepository;

    @BeforeEach
    public void init() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    public void testCreateUserHappy() {
        Mockito.when(userRepository.save(any())).thenReturn(new User("BARK_USER", "BARK_PASSWORD"));

        User user = userService.createUser();

        assertNotNull(user.id());
        assertNotNull(user.password());
        Mockito.verify(userRepository, times(1)).save(any(User.class));
    }

}