package com.gettimhired.model.dto;

import com.gettimhired.model.mongo.Job;

import java.time.LocalDate;
import java.util.List;

public record JobDTO(
        String id,
        String userId,
        String candidateId,
        String companyName,
        String title,
        LocalDate startDate,
        LocalDate endDate,
        List<String> skills,
        List<String> achievements,
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
}
