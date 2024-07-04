package com.gettimhired.service;

import com.gettimhired.model.dto.EducationDTO;
import com.gettimhired.model.mongo.Education;
import com.gettimhired.model.mongo.EducationLevel;
import com.gettimhired.repository.EducationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EducationServiceTest {
    private EducationService educationService;
    private EducationRepository educationRepository;

    @BeforeEach
    public void init() {
        educationRepository = mock(EducationRepository.class);
        educationService = new EducationService(educationRepository);
    }

    @Test
    public void testfindAllEducationsForUserAndCandidateIdHappy() {
        var e1 = new Education(
                UUID.randomUUID().toString(),
                "BARK_USER_ID",
                "BARK_C_ID",
                "BARK_NAME",
                LocalDate.now(),
                LocalDate.now(),
                true,
                "Computer Science",
                EducationLevel.BACHELORS
        );
        var e2 = new Education(
                UUID.randomUUID().toString(),
                "BARK_USER_ID",
                "BARK_C_ID",
                "BARK_NAME_TWO",
                LocalDate.now(),
                LocalDate.now(),
                true,
                "Computer Science",
                EducationLevel.BACHELORS
        );
        var educations = List.of(e1, e2);
        when(educationRepository.findAllByUserIdAndCandidateIdOrderByEndDate("BARK_USER_ID", "BARK_C_ID")).thenReturn(educations);

        var result = educationService.findAllEducationsForUserAndCandidateId("BARK_USER_ID", "BARK_C_ID");

        verify(educationRepository, times(1)).findAllByUserIdAndCandidateIdOrderByEndDate("BARK_USER_ID", "BARK_C_ID");
        assertNotNull(result);
        assertEquals(2, result.size());
    }

}