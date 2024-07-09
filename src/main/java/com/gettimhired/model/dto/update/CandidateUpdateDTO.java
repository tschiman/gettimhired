package com.gettimhired.model.dto.update;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gettimhired.model.dto.input.CandidateInputDTO;
import com.gettimhired.model.mongo.Candidate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CandidateUpdateDTO(
        @NotBlank(message = "First name cannot be blank")
        @Size(min = 1, max = 256, message = "First name must be between 1 and 256 characters")
        String firstName,
        @NotBlank(message = "Last name cannot be blank")
        @Size(min = 1, max = 256, message = "Last name must be between 1 and 256 characters")
        String lastName,
        @Size(max = 4000)
        String summary,
        String linkedInUrl,
        String githubUrl
) {
    public CandidateUpdateDTO(Candidate candidate) {
        this(
                candidate.firstName(),
                candidate.lastName(),
                candidate.summary(),
                candidate.linkedInUrl(),
                candidate.githubUrl()
        );
    }

    public CandidateUpdateDTO(CandidateInputDTO candidate) {
        this(
                candidate.firstName(),
                candidate.lastName(),
                candidate.summary(),
                candidate.linkedInUrl(),
                candidate.githubUrl()
        );
    }
}
