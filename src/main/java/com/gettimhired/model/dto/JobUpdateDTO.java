package com.gettimhired.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public record JobUpdateDTO(
        @NotBlank(message = "Company name cannot be blank")
        @Size(min = 1, max = 256, message = "Company name must be between 1 and 256 characters")
        String companyName,
        @NotBlank(message = "Title cannot be blank")
        @Size(min = 1, max = 256, message = "Title must be between 1 and 256 characters")
        String title,
        @NotNull
        LocalDate startDate,
        LocalDate endDate,
        List<String> skills,
        List<String> achievements,
        @NotNull
        Boolean currentlyWorking
) {
    public JobUpdateDTO(JobInput job) {
        this(
                job.companyName(),
                job.title(),
                job.startDate(),
                job.endDate(),
                job.skills(),
                job.achievements(),
                job.currentlyWorking()
        );
    }
}
