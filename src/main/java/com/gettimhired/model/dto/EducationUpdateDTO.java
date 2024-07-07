package com.gettimhired.model.dto;

import com.gettimhired.model.mongo.EducationLevel;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record EducationUpdateDTO(
        @Size(min = 1, max = 256, message = "Name of school can be between 1 and 256 characters")
        String name,
        LocalDate startDate,
        LocalDate endDate,
        Boolean graduated,
        @Size(min = 1, max = 256, message = "Area of Study can be between 1 and 256 characters")
        String areaOfStudy,
        EducationLevel levelOfEducation
) {
    public EducationUpdateDTO(EducationInput education) {
        this(
                education.name(),
                education.startDate(),
                education.endDate(),
                education.graduated(),
                education.areaOfStudy(),
                education.levelOfEducation()
        );
    }
}
