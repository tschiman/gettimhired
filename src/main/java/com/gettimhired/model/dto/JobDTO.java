package com.gettimhired.model.dto;

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
}
