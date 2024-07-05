package com.gettimhired.model.dto;

import java.time.LocalDate;
import java.util.List;

public record JobUpdateDTO(
        String companyName,
        String title,
        LocalDate startDate,
        LocalDate endDate,
        List<String> skills,
        List<String> achievements,
        Boolean currentlyWorking
) {
}
