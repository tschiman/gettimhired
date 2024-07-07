package com.gettimhired.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CandidateInput(
        String id,
        String userId,
        @NotBlank(message = "First name cannot be blank")
        @Size(min = 1, max = 256, message = "First name must be between 1 and 256 characters")
        String firstName,
        @NotBlank(message = "Last name cannot be blank")
        @Size(min = 1, max = 256, message = "Last name must be between 1 and 256 characters")
        String lastName,
        @Size(max = 4000)
        String summary
) {
}
