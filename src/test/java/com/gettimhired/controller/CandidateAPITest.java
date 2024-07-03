package com.gettimhired.controller;

import com.gettimhired.model.dto.CandidateDTO;
import com.gettimhired.service.CandidateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CandidateAPITest {

    private CandidateAPI candidateAPI;
    private CandidateService candidateService;
    private UserDetails userDetails;

    @BeforeEach
    public void init() {
        candidateService = mock(CandidateService.class);
        userDetails = mock(UserDetails.class);
        candidateAPI = new CandidateAPI(candidateService);
    }

    @Test
    public void testGetAllCandidates() {
        when(userDetails.getUsername()).thenReturn("BARK");
        when(candidateService.findAllCandidatesForUser("BARK")).thenReturn(new ArrayList<>());

        var allCandidates = candidateAPI.getAllCandidates(userDetails);

        verify(userDetails, times(1)).getUsername();
        verify(candidateService, times(1)).findAllCandidatesForUser("BARK");
        assertEquals(0, allCandidates.size());
    }

    @Test
    public void testGetCandidateByUserIdAndIdHappy() {
        var candidateDto = new CandidateDTO("BARK_ID", "BARK_USER_ID", "BARK_FNAME", "BARK_LNAME", "BARK_SUMMARY");
        when(candidateService.findCandidateByUserIdAndId("BARK_USER_ID", "BARK_ID")).thenReturn(Optional.of(candidateDto));
        when(userDetails.getUsername()).thenReturn("BARK_USER_ID");

        var response = candidateAPI.getCandidateById(userDetails, "BARK_ID");

        verify(candidateService, times(1)).findCandidateByUserIdAndId("BARK_USER_ID", "BARK_ID");
        verify(userDetails, times(1)).getUsername();
        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(200) ,response.getStatusCode());
        assertEquals(candidateDto, response.getBody());
    }

    @Test
    public void testGetCandidateByUserIdAndId404() {
        when(candidateService.findCandidateByUserIdAndId("BARK_USER_ID", "BARK_ID")).thenReturn(Optional.empty());
        when(userDetails.getUsername()).thenReturn("BARK_USER_ID");

        var response = candidateAPI.getCandidateById(userDetails, "BARK_ID");

        verify(candidateService, times(1)).findCandidateByUserIdAndId("BARK_USER_ID", "BARK_ID");
        verify(userDetails, times(1)).getUsername();
        assertNotNull(response);
        assertEquals(HttpStatusCode.valueOf(404) ,response.getStatusCode());
        assertNull(response.getBody());
    }

}