package com.gettimhired.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/", params = "candidateId")
    public String index(@RequestParam String candidateId, Model model) {
        model.addAttribute("hasCandidate", true);
        return "index";
    }
}
