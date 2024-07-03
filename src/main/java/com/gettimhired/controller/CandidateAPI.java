package com.gettimhired.controller;

import com.gettimhired.model.dto.CandidateDTO;
import com.gettimhired.service.CandidateService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CandidateDTO> getCandidateById(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String id
    ) {
        var candidateOpt = candidateService.findCandidateByUserIdAndId(userDetails.getUsername(), id);
        return candidateOpt
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CandidateDTO> createCandidate(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Valid CandidateDTO candidateDTO
    ) {
        var candidateDtoOpt = candidateService.createCandidate(userDetails.getUsername(), candidateDTO);
        return candidateDtoOpt
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }
//    @PutMapping("/{id}")
//    @DeleteMapping("/{id}")
}
