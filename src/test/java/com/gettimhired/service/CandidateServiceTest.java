package com.gettimhired.service;

import com.gettimhired.error.CandidateUpdateException;
import com.gettimhired.model.dto.CandidateDTO;
import com.gettimhired.model.dto.CandidateUpdateDTO;
import com.gettimhired.model.mongo.Candidate;
import com.gettimhired.repository.CandidateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.springframework.http.HttpStatus;

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

    @Test
    public void testUpdateCandidateHappy() {
        var candidateUpdateDto = new CandidateUpdateDTO(
                "BARK_FNAME_UPDATE",
                "BARK_LNAME_UPDATE",
                "BARK_SUMMARY_UPDATE"
        );
        var candidate = new Candidate(
                "BARK_ID",
                "BARK_USER_ID",
                "BARK_FNAME",
                "BARK_LNAME",
                "BARK_SUMMARY"
        );
        when(candidateRepository.findById("BARK_ID")).thenReturn(Optional.of(candidate));
        when(candidateRepository.save(any(Candidate.class))).then(AdditionalAnswers.returnsFirstArg());

        var candidateDtoOpt = candidateService.updateCandidate("BARK_ID", "BARK_USER_ID", candidateUpdateDto);

        verify(candidateRepository, times(1)).findById("BARK_ID");
        verify(candidateRepository, times(1)).save(any(Candidate.class));
        assertTrue(candidateDtoOpt.isPresent());
        assertEquals("BARK_ID", candidateDtoOpt.get().id());
        assertEquals("BARK_USER_ID", candidateDtoOpt.get().userId());
        assertEquals("BARK_FNAME_UPDATE", candidateDtoOpt.get().firstName());
        assertEquals("BARK_LNAME_UPDATE", candidateDtoOpt.get().lastName());
        assertEquals("BARK_SUMMARY_UPDATE", candidateDtoOpt.get().summary());
    }

    @Test
    public void testUpdateCandidateDbErrorReturnOptionalEmpty() {
        var candidateUpdateDto = new CandidateUpdateDTO(
                "BARK_FNAME_UPDATE",
                "BARK_LNAME_UPDATE",
                "BARK_SUMMARY_UPDATE"
        );
        var candidate = new Candidate(
                "BARK_ID",
                "BARK_USER_ID",
                "BARK_FNAME",
                "BARK_LNAME",
                "BARK_SUMMARY"
        );
        when(candidateRepository.findById("BARK_ID")).thenReturn(Optional.of(candidate));
        when(candidateRepository.save(any(Candidate.class))).thenThrow(new RuntimeException());

        var candidateDtoOpt = candidateService.updateCandidate("BARK_ID", "BARK_USER_ID", candidateUpdateDto);

        verify(candidateRepository, times(1)).findById("BARK_ID");
        verify(candidateRepository, times(1)).save(any(Candidate.class));

        assertFalse(candidateDtoOpt.isPresent());
    }

    @Test
    public void testUpdateCandidateUserIdDoesNotMatch() {
        var candidateUpdateDto = new CandidateUpdateDTO(
                "BARK_FNAME_UPDATE",
                "BARK_LNAME_UPDATE",
                "BARK_SUMMARY_UPDATE"
        );
        var candidate = new Candidate(
                "BARK_ID",
                "BARK_USER_ID_MISMATCH",
                "BARK_FNAME",
                "BARK_LNAME",
                "BARK_SUMMARY"
        );
        when(candidateRepository.findById("BARK_ID")).thenReturn(Optional.of(candidate));

        var ex = assertThrows(CandidateUpdateException.class, () -> candidateService.updateCandidate("BARK_ID", "BARK_USER_ID", candidateUpdateDto));

        verify(candidateRepository, times(1)).findById("BARK_ID");
        verify(candidateRepository, times(0)).save(any(Candidate.class));
        assertEquals(HttpStatus.FORBIDDEN, ex.getHttpStatus());
    }

    @Test
    public void testUpdateCandidateNotFound() {
        var candidateUpdateDto = new CandidateUpdateDTO(
                "BARK_FNAME_UPDATE",
                "BARK_LNAME_UPDATE",
                "BARK_SUMMARY_UPDATE"
        );
        when(candidateRepository.findById("BARK_ID")).thenReturn(Optional.empty());

        var ex = assertThrows(CandidateUpdateException.class, () -> candidateService.updateCandidate("BARK_ID", "BARK_USER_ID", candidateUpdateDto));

        verify(candidateRepository, times(1)).findById("BARK_ID");
        verify(candidateRepository, times(0)).save(any(Candidate.class));
        assertEquals(HttpStatus.NOT_FOUND, ex.getHttpStatus());
    }

    @Test
    public void testDeleteCandidateHappy() {
        doNothing().when(candidateRepository).deleteByIdAndUserId("BARK_ID", "BARK_USER_ID");

        var result = candidateService.deleteCandidate("BARK_ID", "BARK_USER_ID");

        assertTrue(result);
    }

    @Test
    public void testDeleteCandidateTHrowsException() {
        doThrow(new RuntimeException()).when(candidateRepository).deleteByIdAndUserId("BARK_ID", "BARK_USER_ID");

        var result = candidateService.deleteCandidate("BARK_ID", "BARK_USER_ID");

        assertFalse(result);
    }

}