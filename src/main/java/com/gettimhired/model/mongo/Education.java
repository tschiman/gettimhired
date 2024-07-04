package com.gettimhired.model.mongo;

import org.springframework.data.annotation.Id;

import java.time.LocalDate;

public record Education(
        @Id String id,
        String userId,
        String candidateId,
        String name,
        LocalDate startDate,
        LocalDate endDate,
        Boolean graduated,
        String areaOfStudy,
        EducationLevel levelOfEducation
) {
}
