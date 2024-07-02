package com.gettimhired.repository;

import com.gettimhired.model.mongo.Candidate;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CandidateRepository extends MongoRepository<Candidate, String> {
    List<Candidate> findAllByUserIdOrderByLastName(String username);
}
