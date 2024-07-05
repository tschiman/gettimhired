package com.gettimhired.controller;

import com.gettimhired.model.dto.CandidateDTO;
import com.gettimhired.model.dto.EducationDTO;
import com.gettimhired.model.dto.JobDTO;
import com.gettimhired.service.CandidateService;
import com.gettimhired.service.EducationService;
import com.gettimhired.service.JobService;
import com.gettimhired.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class MainController {

    private final UserService userService;
    private final CandidateService candidateService;
    private final EducationService educationService;
    private final JobService jobService;

    public MainController(UserService userService, CandidateService candidateService, EducationService educationService, JobService jobService) {
        this.userService = userService;
        this.candidateService = candidateService;
        this.educationService = educationService;
        this.jobService = jobService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("candidates", candidateService.findAllCandidates());
        return "index";
    }

    @GetMapping(value = "/", params = "candidateId")
    public String index(@RequestParam String candidateId, Model model) {
        Optional<CandidateDTO> candidate = candidateService.findCandidateById(candidateId);
        List<EducationDTO> educations = educationService.findAllEducationsByCandidateId(candidateId);
        List<JobDTO> jobs = jobService.findAllJobsByCandidateId(candidateId);

        model
                .addAttribute("hasCandidate", true)
                .addAttribute("candidate", candidate.orElse(null))
                .addAttribute("educations", educations)
                .addAttribute("jobs", jobs);

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
        model
                .addAttribute("user", user.id())
                .addAttribute("password", user.password());
        return "credentials";
    }
}
