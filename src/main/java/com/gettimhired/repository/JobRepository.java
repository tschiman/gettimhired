package com.gettimhired.repository;

import com.gettimhired.model.mongo.Job;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JobRepository extends MongoRepository<Job, String> {
}
