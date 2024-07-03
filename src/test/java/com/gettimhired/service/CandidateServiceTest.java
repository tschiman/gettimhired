package com.gettimhired.service;

import com.gettimhired.model.dto.CandidateDTO;
import com.gettimhired.repository.CandidateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
    public void test() {
        when(candidateRepository.findAllByUserIdOrderByLastName("BARK")).thenReturn(new ArrayList<>());

        var candidates = candidateService.findAllCandidatesForUser("BARK");

        verify(candidateRepository, times(1)).findAllByUserIdOrderByLastName("BARK");
        assertEquals(0, candidates.size());
    }

}