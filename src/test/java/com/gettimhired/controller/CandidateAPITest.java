package com.gettimhired.controller;

import com.gettimhired.model.dto.CandidateDTO;
import com.gettimhired.service.CandidateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

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

}