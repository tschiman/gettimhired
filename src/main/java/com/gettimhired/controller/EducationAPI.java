package com.gettimhired.controller;

import com.gettimhired.service.EducationService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/candidates/{candidateId}/educations")
public class EducationAPI {

    private final EducationService educationService;

    public EducationAPI(EducationService educationService) {
        this.educationService = educationService;
    }

    //GET - all educations for candidate
    //GET /{id} - one education
    //POST - create education for candidate
    //PUT /{id} - update an education
    //DELETE /{id} delete education
}
