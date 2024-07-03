package com.gettimhired.controller;

import com.gettimhired.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    private final UserService userService;

    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping(value = "/", params = "candidateId")
    public String index(@RequestParam String candidateId, Model model) {
        model.addAttribute("hasCandidate", true);
        return "index";
    }

    @GetMapping("/credentials")
    public String api() {
        return "credentials";
    }

    @PostMapping("/credentials")
    public String createCredentials(Model model) {
        //create a user
        var user = userService.createUser();
        //put credentials in model to view them
        model.addAttribute("user", user.id());
        model.addAttribute("password", user.password());
        return "credentials";
    }
}
