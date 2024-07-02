package com.gettimhired.service;

import com.gettimhired.model.mongo.User;
import com.gettimhired.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser() {
        var user = new User(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        userRepository.save(user);
        return user;
    }
}
