package com.gettimhired.service;

import com.gettimhired.model.mongo.User;
import com.gettimhired.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser() {
        var password = UUID.randomUUID().toString();
        var user = new User(UUID.randomUUID().toString(), passwordEncoder.encode(password));
        userRepository.save(user);
        return new User(user.id(), password);
    }

    public Optional<User> findUserByUsername(String id) {
        return userRepository.findById(id);
    }
}
