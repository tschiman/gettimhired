package com.gettimhired.service;

import com.gettimhired.model.dto.CandidateDTO;
import com.gettimhired.model.dto.EducationDTO;
import com.gettimhired.repository.EducationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EducationService {

    private final EducationRepository educationRepository;

    public EducationService(EducationRepository educationRepository) {
        this.educationRepository = educationRepository;
    }

    public List<EducationDTO> findAllEducationsForUserAndCandidateId(String userId, String candidateId) {
        return educationRepository.findAllByUserIdAndCandidateIdOrderByEndDate(userId, candidateId).stream()
                .map(EducationDTO::new)
                .toList();
    }
}
