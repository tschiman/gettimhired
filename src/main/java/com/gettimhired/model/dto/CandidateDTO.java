package com.gettimhired.model.dto;

import com.gettimhired.model.mongo.Candidate;

public record CandidateDTO(
        String id,
        String userId,
        String firstName,
        String lastName,
        String summary
) {
    public CandidateDTO(Candidate candidate) {
        this(
                candidate.id(),
                candidate.userId(),
                candidate.firstName(),
                candidate.lastName(),
                candidate.summary()
        );
    }
}
