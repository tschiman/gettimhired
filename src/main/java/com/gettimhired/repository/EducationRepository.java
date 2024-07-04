package com.gettimhired.repository;

import com.gettimhired.model.mongo.Education;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface EducationRepository extends MongoRepository<Education, String> {
    List<Education> findAllByUserIdAndCandidateIdOrderByEndDate(String userId, String candidateId);

    Optional<Education> findEducationByUserIdAndCandidateIdAndId(String userId, String candidateId, String id);

    void deleteByIdAndUserIdAndCandidateId(String id, String userId, String candidateId);
}
