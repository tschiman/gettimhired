package com.gettimhired.service;

import com.gettimhired.error.APIUpdateException;
import com.gettimhired.model.dto.CandidateDTO;
import com.gettimhired.model.dto.CandidateUpdateDTO;
import com.gettimhired.model.dto.EducationDTO;
import com.gettimhired.model.dto.EducationUpdateDTO;
import com.gettimhired.model.mongo.Candidate;
import com.gettimhired.model.mongo.Education;
import com.gettimhired.repository.EducationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<EducationDTO> findEducationByUserIdAndCandidateIdAndId(String userId, String candidateId, String id) {
        return educationRepository.findEducationByUserIdAndCandidateIdAndId(userId, candidateId, id).map(EducationDTO::new);
    }

    public Optional<EducationDTO> createEducation(String userId, String candidateId, EducationDTO educationDTO) {
        var education = new Education(userId, candidateId, educationDTO);
        try {
            var educationFromDb = educationRepository.save(education);
            var educationDtoFromDatabase = new EducationDTO(educationFromDb);
            return Optional.of(educationDtoFromDatabase);
        } catch (Exception e) {
            //add logging here
            return Optional.empty();
        }
    }

    public Optional<EducationDTO> updateEducation(String id, String userId, String candidateId, EducationUpdateDTO educationUpdateDTO) {
        //get education from db
        Optional<Education> educationOpt = educationRepository.findById(id);
        if (educationOpt.isPresent()) {
            //check if the username matches
            if (educationOpt.get().userId().equals(userId)) {
                //check the candidateId
                if (educationOpt.get().candidateId().equals(candidateId)) {
                    //then update the candidate values
                    var educationToSave = new Education(
                            educationOpt.get().id(),
                            educationOpt.get().userId(),
                            educationOpt.get().candidateId(),
                            educationUpdateDTO.name(),
                            educationUpdateDTO.startDate(),
                            educationUpdateDTO.endDate(),
                            educationUpdateDTO.graduated(),
                            educationUpdateDTO.areaOfStudy(),
                            educationUpdateDTO.levelOfEducation()
                    );
                    Education educationToReturn;
                    try {
                        educationToReturn = educationRepository.save(educationToSave);
                    } catch (Exception e) {
                        //log
                        return Optional.empty();
                    }
                    var educationDTO = new EducationDTO(educationToReturn);
                    return Optional.of(educationDTO);
                } else {
                    throw new APIUpdateException(HttpStatus.FORBIDDEN);
                }
            } else {
                //userId does not match (403)
                throw new APIUpdateException(HttpStatus.FORBIDDEN);
            }
        } else {
            //CandidateId not found(404)
            throw new APIUpdateException(HttpStatus.NOT_FOUND);
        }
    }

    public boolean deleteEducation(String id, String userId, String candidateId) {
        try {
            educationRepository.deleteByIdAndUserIdAndCandidateId(id, userId, candidateId);
            return true;
        } catch (Exception e) {
            //log
            return false;
        }
    }
}
