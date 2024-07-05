package com.gettimhired.service;


import com.gettimhired.error.APIUpdateException;
import com.gettimhired.model.dto.JobDTO;
import com.gettimhired.model.dto.JobUpdateDTO;
import com.gettimhired.model.mongo.Job;
import com.gettimhired.repository.JobRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public List<JobDTO> findAllJobsForUserAndCandidateId(String userId, String candidateId) {
        return jobRepository.findAllByUserIdAndCandidateId(userId, candidateId).stream()
                .map(JobDTO::new)
                .toList();
    }

    public Optional<JobDTO> findJobByUserIdAndCandidateIdAndId(String userId, String candidateId, String id) {
        return jobRepository.findJobByUserIdAndCandidateIdAndId(userId, candidateId, id)
                .map(JobDTO::new);
    }

    public Optional<JobDTO> createJob(String userId, String candidateId, JobDTO jobDto) {
        var job = new Job(userId, candidateId, jobDto);
        try {
            var jobFromDb = jobRepository.save(job);
            var jobFromDatabase = new JobDTO(jobFromDb);
            return Optional.of(jobFromDatabase);
        } catch (Exception e) {
            //add logging here
            return Optional.empty();
        }
    }

    public Optional<JobDTO> updateJob(String id, String userId, String candidateId, JobUpdateDTO jobUpdateDTO) {
        //get job from db
        Optional<Job> jobOpt = jobRepository.findById(id);
        if (jobOpt.isPresent()) {
            //check if the username matches
            if (jobOpt.get().userId().equals(userId)) {
                //check the candidateId
                if (jobOpt.get().candidateId().equals(candidateId)) {
                    //then update the candidate values
                    var jobToSave = new Job(
                            jobOpt.get().id(),
                            jobOpt.get().userId(),
                            jobOpt.get().candidateId(),
                            jobUpdateDTO.companyName(),
                            jobUpdateDTO.title(),
                            jobUpdateDTO.startDate(),
                            jobUpdateDTO.endDate(),
                            jobUpdateDTO.skills(),
                            jobUpdateDTO.achievements(),
                            jobUpdateDTO.currentlyWorking()
                    );
                    Job jobToReturn;
                    try {
                        jobToReturn = jobRepository.save(jobToSave);
                    } catch (Exception e) {
                        //log
                        return Optional.empty();
                    }
                    var jobDto = new JobDTO(jobToReturn);
                    return Optional.of(jobDto);
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

    public boolean deleteJob(String id, String userId, String candidateId) {
        try {
            jobRepository.deleteByIdAndUserIdAndCandidateId(id, userId, candidateId);
            return true;
        } catch (Exception e) {
            //log
            return false;
        }
    }

    public List<JobDTO> findAllJobsByCandidateId(String candidateId) {
        return jobRepository.findAllByCandidateId(candidateId);
    }
}
