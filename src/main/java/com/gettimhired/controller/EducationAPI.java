package com.gettimhired.controller;

import com.gettimhired.error.APIUpdateException;
import com.gettimhired.model.dto.EducationDTO;
import com.gettimhired.model.dto.EducationUpdateDTO;
import com.gettimhired.service.EducationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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
    public List<EducationDTO> getAllEducations(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String candidateId
    ) {
        return educationService
                .findAllEducationsForUserAndCandidateId(
                        userDetails.getUsername(),
                        candidateId
                );
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EducationDTO> getEducationById(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String id,
            @PathVariable String candidateId
    ) {
        var educationOpt = educationService.findEducationByIdAndUserId(id, userDetails.getUsername());
        return educationOpt
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EducationDTO> createEducation(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Valid EducationDTO educationDTO,
            @PathVariable String candidateId
    ) {
        var educationDtoOpt = educationService.createEducation(userDetails.getUsername(), candidateId, educationDTO);
        return educationDtoOpt
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EducationDTO> updateEducation(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Valid EducationUpdateDTO educationUpdateDTO,
            @PathVariable String id,
            @PathVariable String candidateId
    ) {
        try {
            var educationDtoOpt = educationService.updateEducation(id, userDetails.getUsername(), candidateId, educationUpdateDTO);
            return educationDtoOpt
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
        } catch (APIUpdateException e) {
            return ResponseEntity.status(e.getHttpStatus()).build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity deleteEducation(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String id,
            @PathVariable String candidateId
    ) {
        boolean result = educationService.deleteEducation(id, userDetails.getUsername(), candidateId);
        return result ?
                ResponseEntity.ok().build() :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
