package com.gettimhired.controller;

import com.gettimhired.model.dto.UserDTO;
import com.gettimhired.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserAPI {

    private final UserService userService;

    public UserAPI(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/migrate")
    public List<UserDTO> migrateUsers() {
        return userService.findAll();
    }
}
