package com.gettimhired.controller;

import com.gettimhired.model.dto.CandidateDTO;
import com.gettimhired.model.dto.EducationDTO;
import com.gettimhired.model.dto.JobDTO;
import com.gettimhired.model.dto.SignUpFormDTO;
import com.gettimhired.service.CandidateService;
import com.gettimhired.service.EducationService;
import com.gettimhired.service.JobService;
import com.gettimhired.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class MainController {

    Logger log = LoggerFactory.getLogger(MainController.class);
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
        log.info("GET / index");
        model.addAttribute("candidates", candidateService.findAllCandidates());
        return "index";
    }

    @GetMapping(value = "/", params = "candidateId")
    public String index(@RequestParam String candidateId, Model model) {
        log.info("GET /?candidateId index candidateId={}", candidateId);
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

    @GetMapping("/signup")
    public String signup(Model model) {
        log.info("GET /signup signup");
        model.addAttribute("signUpForm", new SignUpFormDTO(null, null, null));
        return "signups";
    }

    @PostMapping("/signup")
    public String signUp(@Valid @ModelAttribute SignUpFormDTO signupForm, BindingResult bindingResult, Model model) {
        if (!signupForm.password().equals(signupForm.passwordCopy())) {
            bindingResult.addError(new ObjectError("password", "Passwords must match"));
        }
        if (bindingResult.hasErrors()) {
            return "signups";
        }
        userService.createUser(signupForm.email(), signupForm.password());
        return "redirect:/login";
    }

//    @PostMapping("/signup")
//    public String createCredentials(Model model) {
//        log.info("POST /credentials createCredentials");
//        //create a user
//        var user = userService.createUser();
//        //put credentials in model to view them
//        model
//                .addAttribute("user", user.id())
//                .addAttribute("password", user.password());
//        return "credentials";
//    }

    @GetMapping("/postman")
    public String postman() {
        log.info("GET /postman postman");
        return "postmans";
    }

    @GetMapping("/private")
    public String priv() {
        log.info("GET /private priv");
        return "privates";
    }
}
