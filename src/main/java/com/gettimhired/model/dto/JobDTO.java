package com.gettimhired.model.dto;

import com.gettimhired.model.dto.input.JobInputDTO;
import com.gettimhired.model.mongo.Job;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public record JobDTO(
        String id,
        String userId,
        String candidateId,
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
    public JobDTO(Job job) {
        this (
                job.id(),
                job.userId(),
                job.candidateId(),
                job.companyName(),
                job.title(),
                job.startDate(),
                job.endDate(),
                job.skills(),
                job.achievements(),
                job.currentlyWorking()
        );
    }

    public JobDTO(JobInputDTO job) {
        this(
                job.id(),
                job.userId(),
                job.candidateId(),
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
