package com.gettimhired.service;

import com.gettimhired.error.CandidateUpdateException;
import com.gettimhired.model.dto.CandidateDTO;
import com.gettimhired.model.dto.CandidateUpdateDTO;
import com.gettimhired.model.mongo.Candidate;
import com.gettimhired.repository.CandidateRepository;
import org.springframework.http.HttpStatus;
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

    public Optional<CandidateDTO> updateCandidate(String id, String userId, CandidateUpdateDTO candidateUpdateDTO) {
        //get candidate from db
        Optional<Candidate> candidateOpt = candidateRepository.findById(id);
        if (candidateOpt.isPresent()) {
            //check if the username matches
            if (candidateOpt.get().userId().equals(userId)) {
                //then update the candidate values
                var candidateToSave = new Candidate(
                        candidateOpt.get().id(),
                        candidateOpt.get().userId(),
                        candidateUpdateDTO.firstName(),
                        candidateUpdateDTO.lastName(),
                        candidateUpdateDTO.summary()
                );
                Candidate candidateToReturn;
                try {
                    candidateToReturn = candidateRepository.save(candidateToSave);
                } catch (Exception e) {
                    //log
                    return Optional.empty();
                }
                var candidateDto = new CandidateDTO(candidateToReturn);
                return Optional.of(candidateDto);
            } else {
                //userId does not match (403)
                throw new CandidateUpdateException(HttpStatus.FORBIDDEN);
            }
        } else {
            //CandidateId not found(404)
            throw new CandidateUpdateException(HttpStatus.NOT_FOUND);
        }
    }

    public boolean deleteCandidate(String id, String userId) {
        try {
            candidateRepository.deleteByIdAndUserId(id, userId);
            return true;
        } catch (Exception e) {
            //log
            return false;
        }
    }
}
