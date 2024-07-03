package com.gettimhired.service;

import com.gettimhired.model.dto.CandidateDTO;
import com.gettimhired.model.mongo.Candidate;
import com.gettimhired.repository.CandidateRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public Optional<CandidateDTO> findCandidateByUserIdAndId(String userId, String id) {
        return candidateRepository.findCandidateByUserIdAndId(userId, id).map(CandidateDTO::new);
    }

    public Optional<CandidateDTO> createCandidate(String userId, CandidateDTO candidateDTO) {
        var candidate = new Candidate(userId, candidateDTO);
        try {
            var candidateFromDb = candidateRepository.save(candidate);
            var candidateDtoFromDatabase = new CandidateDTO(candidateFromDb);
            return Optional.of(candidateDtoFromDatabase);
        } catch (Exception e) {
            //add logging here
            return Optional.empty();
        }
    }
}
