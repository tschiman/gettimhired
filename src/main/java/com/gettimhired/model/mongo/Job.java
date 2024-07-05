package com.gettimhired.model.mongo;

import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.List;

public record Job(
    @Id String id,
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
