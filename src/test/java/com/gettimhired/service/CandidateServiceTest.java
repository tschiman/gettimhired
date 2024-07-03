package com.gettimhired.service;

import com.gettimhired.model.dto.CandidateDTO;
import com.gettimhired.model.mongo.Candidate;
import com.gettimhired.repository.CandidateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CandidateServiceTest {

    private CandidateService candidateService;
    private CandidateRepository candidateRepository;

    @BeforeEach
    public void init() {
        candidateRepository = mock(CandidateRepository.class);
        candidateService = new CandidateService(candidateRepository);
    }

    @Test
    public void testFindAllCandidatesEmptyArrayList() {
        when(candidateRepository.findAllByUserIdOrderByLastName("BARK")).thenReturn(new ArrayList<>());

        var candidates = candidateService.findAllCandidatesForUser("BARK");

        verify(candidateRepository, times(1)).findAllByUserIdOrderByLastName("BARK");
        assertEquals(0, candidates.size());
    }

    @Test
    public void testFindCandidateByIdHappy() {
        var candidate = new Candidate("BARK_ID", "BARK_USER_ID", "BARK_FNAME", "BARK_LNAME", "BARK_SUMMARY");
        when(candidateRepository.findCandidateByUserIdAndId("BARK_USER_ID", "BARK_ID")).thenReturn(Optional.of(candidate));

        var result = candidateService.findCandidateByUserIdAndId("BARK_USER_ID", "BARK_ID");

        verify(candidateRepository, times(1)).findCandidateByUserIdAndId("BARK_USER_ID", "BARK_ID");
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals("BARK_ID", result.get().id());
        assertEquals("BARK_USER_ID", result.get().userId());
        assertEquals("BARK_FNAME", result.get().firstName());
        assertEquals("BARK_LNAME", result.get().lastName());
        assertEquals("BARK_SUMMARY", result.get().summary());
    }

    @Test
    public void testFindCandidateByIdNoCandidate() {
        when(candidateRepository.findCandidateByUserIdAndId("BARK_USER_ID", "BARK_ID")).thenReturn(Optional.empty());

        var result = candidateService.findCandidateByUserIdAndId("BARK_USER_ID", "BARK_ID");

        verify(candidateRepository, times(1)).findCandidateByUserIdAndId("BARK_USER_ID", "BARK_ID");
        assertNotNull(result);
        assertFalse(result.isPresent());
    }

    @Test
    public void testCreateCandidateHappy() {
        var candidateDtoIn = new CandidateDTO(null, null, "Bark", "McBarkson", "Summary Bark");
        var candidateOut = new Candidate("BARK_USER_ID", candidateDtoIn);
        when(candidateRepository.save(any(Candidate.class))).thenReturn(candidateOut);

        var candidateDtoOpt = candidateService.createCandidate("BARK_USER_ID", candidateDtoIn);

        verify(candidateRepository, times(1)).save(any(Candidate.class));
        assertTrue(candidateDtoOpt.isPresent());
        assertNotNull(candidateDtoOpt.get().id());
        assertNotNull(candidateDtoOpt.get().userId());
        assertEquals("BARK_USER_ID", candidateDtoOpt.get().userId());
        assertNotNull(candidateDtoOpt.get().firstName());
        assertNotNull(candidateDtoOpt.get().lastName());
        assertNotNull(candidateDtoOpt.get().summary());
    }

    @Test
    public void testCreateCandidateThrowsException() {
        var candidateDtoIn = new CandidateDTO(null, null, "Bark", "McBarkson", "Summary Bark");
        var candidateOut = new Candidate("BARK_USER_ID", candidateDtoIn);
        when(candidateRepository.save(any(Candidate.class))).thenThrow(new RuntimeException());

        var candidateDtoOpt = candidateService.createCandidate("BARK_USER_ID", candidateDtoIn);

        verify(candidateRepository, times(1)).save(any(Candidate.class));
        assertFalse(candidateDtoOpt.isPresent());
    }


}