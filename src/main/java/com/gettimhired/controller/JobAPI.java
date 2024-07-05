package com.gettimhired.controller;

import com.gettimhired.service.JobService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/candidates/{candidateId}/jobs")
public class JobAPI {

    private final JobService jobService;

    public JobAPI(JobService jobService) {
        this.jobService = jobService;
    }

//    @GetMapping
//    @PreAuthorize("isAuthenticated()")
//    public List<EducationDTO> getAllEducations(
//            @AuthenticationPrincipal UserDetails userDetails,
//            @PathVariable String candidateId
//    ) {
//        return jobService
//                .findAllEducationsForUserAndCandidateId(
//                        userDetails.getUsername(),
//                        candidateId
//                );
//    }
//
//    @GetMapping("/{id}")
//    @PreAuthorize("isAuthenticated()")
//    public ResponseEntity<EducationDTO> getEducationById(
//            @AuthenticationPrincipal UserDetails userDetails,
//            @PathVariable String id,
//            @PathVariable String candidateId
//    ) {
//        var educationOpt = jobService.findEducationByUserIdAndCandidateIdAndId(userDetails.getUsername(), candidateId, id);
//        return educationOpt
//                .map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
//    }
//
//    @PostMapping
//    @PreAuthorize("isAuthenticated()")
//    public ResponseEntity<EducationDTO> createEducation(
//            @AuthenticationPrincipal UserDetails userDetails,
//            @RequestBody @Valid EducationDTO educationDTO,
//            @PathVariable String candidateId
//    ) {
//        var educationDtoOpt = jobService.createEducation(userDetails.getUsername(), candidateId, educationDTO);
//        return educationDtoOpt
//                .map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
//    }
//
//    @PutMapping("/{id}")
//    @PreAuthorize("isAuthenticated()")
//    public ResponseEntity<EducationDTO> updateEducation(
//            @AuthenticationPrincipal UserDetails userDetails,
//            @RequestBody @Valid EducationUpdateDTO educationUpdateDTO,
//            @PathVariable String id,
//            @PathVariable String candidateId
//    ) {
//        try {
//            var educationDtoOpt = jobService.updateEducation(id, userDetails.getUsername(), candidateId, educationUpdateDTO);
//            return educationDtoOpt
//                    .map(ResponseEntity::ok)
//                    .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
//        } catch (APIUpdateException e) {
//            return ResponseEntity.status(e.getHttpStatus()).build();
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    @PreAuthorize("isAuthenticated()")
//    public ResponseEntity deleteEducation(
//            @AuthenticationPrincipal UserDetails userDetails,
//            @PathVariable String id,
//            @PathVariable String candidateId
//    ) {
//        boolean result = jobService.deleteEducation(id, userDetails.getUsername(), candidateId);
//        return result ?
//                ResponseEntity.ok().build() :
//                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//    }
}
