package com.gettimhired.repository;

import com.gettimhired.model.mongo.Education;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Arrays;
import java.util.List;

public interface EducationRepository extends MongoRepository<Education, String> {
    List<Education> findAllByUserIdAndCandidateIdOrderByEndDate(String userId, String candidateId);
}
