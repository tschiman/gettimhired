package com.gettimhired.controller;

import com.gettimhired.error.APIUpdateException;
import com.gettimhired.model.dto.JobDTO;
import com.gettimhired.model.dto.JobUpdateDTO;
import com.gettimhired.service.JobService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidates/{candidateId}/jobs")
public class JobAPI {

    private final JobService jobService;

    public JobAPI(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<JobDTO> getAllJobs(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String candidateId
    ) {
        return jobService
                .findAllJobsForUserAndCandidateId(
                        userDetails.getUsername(),
                        candidateId
                );
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<JobDTO> getJobById(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String id,
            @PathVariable String candidateId
    ) {
        var jobOpt = jobService.findJobByUserIdAndCandidateIdAndId(userDetails.getUsername(), candidateId, id);
        return jobOpt
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<JobDTO> createJob(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Valid JobDTO jobDTO,
            @PathVariable String candidateId
    ) {
        var jobDtoOpt = jobService.createJob(userDetails.getUsername(), candidateId, jobDTO);
        return jobDtoOpt
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<JobDTO> updateJob(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Valid JobUpdateDTO jobUpdateDTO,
            @PathVariable String id,
            @PathVariable String candidateId
    ) {
        try {
            var jobDtoOpt = jobService.updateJob(id, userDetails.getUsername(), candidateId, jobUpdateDTO);
            return jobDtoOpt
                    .map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
        } catch (APIUpdateException e) {
            return ResponseEntity.status(e.getHttpStatus()).build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity deleteJob(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String id,
            @PathVariable String candidateId
    ) {
        boolean result = jobService.deleteJob(id, userDetails.getUsername(), candidateId);
        return result ?
                ResponseEntity.ok().build() :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
