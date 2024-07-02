package com.gettimhired.service;

import com.gettimhired.model.dto.CandidateDTO;
import com.gettimhired.repository.CandidateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateService {

    private final CandidateRepository candidateRepository;

    public CandidateService(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    public List<CandidateDTO> findAllCandidatesForUser(String username) {
        return candidateRepository.findAllByUserIdOrderByLastName(username).stream()
                .map(CandidateDTO::new)
                .toList();
    }
}
