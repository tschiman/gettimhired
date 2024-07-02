package com.gettimhired.model.mongo;

import org.springframework.data.annotation.Id;

public record Candidate(
        @Id String id,
        String userId,
        String firstName,
        String lastName,
        String summary
) {
}
