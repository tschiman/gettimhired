package com.gettimhired.repository;

import com.gettimhired.model.mongo.Education;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EducationRepository extends MongoRepository<Education, String> {
}
