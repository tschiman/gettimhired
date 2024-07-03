package com.gettimhired.controller;

import com.gettimhired.model.dto.CandidateDTO;
import com.gettimhired.model.mongo.User;
import com.gettimhired.service.CandidateService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidates")
public class CandidateAPI {

    private final CandidateService candidateService;

    public CandidateAPI(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<CandidateDTO> getAllCandidates(@AuthenticationPrincipal UserDetails userDetails) {
        return candidateService.findAllCandidatesForUser(userDetails.getUsername());
    }
//    @GetMapping("/{id}")
//    @PostMapping()
//    @PutMapping("/{id}")
//    @DeleteMapping("/{id}")
}
