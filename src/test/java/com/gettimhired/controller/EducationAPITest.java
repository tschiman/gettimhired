package com.gettimhired.controller;

import com.gettimhired.model.dto.EducationDTO;
import com.gettimhired.service.EducationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EducationAPITest {

    private EducationAPI educationAPI;
    private EducationService educationService;
    private UserDetails userDetails;

    @BeforeEach
    public void init() {
        userDetails = mock(UserDetails.class);
        educationService = mock(EducationService.class);
        educationAPI = new EducationAPI(educationService);
    }

    @Test
    public void testGetAllEducationsHappy() {
        when(userDetails.getUsername()).thenReturn("BARK_USER_ID");
        when(educationService
                .findAllEducationsForUserAndCandidateId(
                        "BARK_USER_ID",
                        "BARK_C_ID"))
                .thenReturn(new ArrayList<>());

        var result = educationAPI.getAllEducations(userDetails, "BARK_C_ID");

        verify(userDetails, times(1)).getUsername();
        verify(educationService, times(1))
                .findAllEducationsForUserAndCandidateId(
                    "BARK_USER_ID",
                    "BARK_C_ID"
                );
        assertNotNull(result);
        assertEquals(0, result.size());
    }


}