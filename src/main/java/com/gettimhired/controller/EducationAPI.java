package com.gettimhired.controller;

import com.gettimhired.model.dto.CandidateDTO;
import com.gettimhired.model.dto.EducationDTO;
import com.gettimhired.service.EducationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/candidates/{candidateId}/educations")
public class EducationAPI {

    private final EducationService educationService;

    public EducationAPI(EducationService educationService) {
        this.educationService = educationService;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<EducationDTO> getAllCandidates(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String candidateId
    ) {
        return educationService.findAllEducationsForUserAndCandidateId(userDetails.getUsername(), candidateId);
    }
    //GET /{id} - one education
    //POST - create education for candidate
    //PUT /{id} - update an education
    //DELETE /{id} delete education
}
