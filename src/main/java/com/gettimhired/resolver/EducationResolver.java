package com.gettimhired.resolver;

import com.gettimhired.model.dto.EducationDTO;
import com.gettimhired.service.EducationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class EducationResolver {

    Logger log = LoggerFactory.getLogger(EducationResolver.class);

    private final EducationService educationService;

    public EducationResolver(EducationService educationService) {
        this.educationService = educationService;
    }

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public List<EducationDTO> getEducations(@AuthenticationPrincipal UserDetails userDetails, @Argument String candidateId) {
        log.info("GQL getEducations userId={} candidateId={}", userDetails.getUsername(), candidateId);
        return educationService.findAllEducationsForUserAndCandidateId(userDetails.getUsername(), candidateId);
    }

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public EducationDTO getEducationById(@AuthenticationPrincipal UserDetails userDetails, @Argument String id) {
        log.info("GQL getEducationById userId={} id={}", userDetails.getUsername(), id);
        return educationService.findEducationByIdAndUserId(id, userDetails.getUsername()).orElse(null);
    }
}
