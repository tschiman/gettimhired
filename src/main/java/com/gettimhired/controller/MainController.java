package com.gettimhired.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class MainController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping(value = "/", params = "candidateId")
    public String index(@RequestParam String candidateId, Model model) {
        model.addAttribute("hasCandidate", true);
        return "index";
    }

    @GetMapping("/api")
    public String api() {
        return "api";
    }

    @PostMapping("/api")
    public String getCredentials(Model model) {
        //create a user
        //create credentials
        //save to db
        //put credentials in model to view them
        model.addAttribute("user", UUID.randomUUID().toString());
        model.addAttribute("password", UUID.randomUUID().toString());
        return "api";
    }
}
