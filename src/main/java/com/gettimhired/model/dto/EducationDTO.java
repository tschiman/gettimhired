package com.gettimhired.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gettimhired.model.mongo.Candidate;
import com.gettimhired.model.mongo.Education;
import com.gettimhired.model.mongo.EducationLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EducationDTO(
        String id,
        String userId,
        String candidateId,
        @NotBlank(message = "Name cannot be blank")
        @Size(min = 1, max = 256, message = "Name can be between 1 and 256 characters")
        String name,
        LocalDate startDate,
        LocalDate endDate,
        Boolean graduated,
        @NotBlank(message = "Area of Study cannot be blank")
        @Size(min = 1, max = 256, message = "Area of Study can be between 1 and 256 characters")
        String areaOfStudy,
        EducationLevel levelOfEducation
) {
        public EducationDTO(Education education) {
                this(
                        education.id(),
                        education.userId(),
                        education.candidateId(),
                        education.name(),
                        education.startDate(),
                        education.endDate(),
                        education.graduated(),
                        education.areaOfStudy(),
                        education.levelOfEducation()
                );
        }
}