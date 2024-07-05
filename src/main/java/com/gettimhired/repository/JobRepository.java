package com.gettimhired.repository;

import com.gettimhired.model.mongo.Job;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface JobRepository extends MongoRepository<Job, String> {
    List<Job> findAllJobsForUserIdAndCandidateId(String userId, String candidateId);

    Optional<Job> findJobByUserIdAndCandidateIdAndId(String userId, String candidateId, String id);

    void deleteByIdAndUserIdAndCandidateId(String id, String userId, String candidateId);
}
