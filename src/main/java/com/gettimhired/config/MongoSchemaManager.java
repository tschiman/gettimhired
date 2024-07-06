package com.gettimhired.config;

import com.gettimhired.model.mongo.ChangeSet;
import com.gettimhired.model.mongo.Education;
import com.gettimhired.repository.ChangeSetRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.stereotype.Component;

@Component
public class MongoSchemaManager {
    private final MongoTemplate mongoTemplate;
    private final ChangeSetRepository changeSetRepository;

    public MongoSchemaManager(MongoTemplate mongoTemplate, ChangeSetRepository changeSetRepository) {
        this.mongoTemplate = mongoTemplate;
        this.changeSetRepository = changeSetRepository;
    }

    @PostConstruct
    public void init() {
        doChangeSet(
                "changeset-001",
                "tim.schimandle",
                "add index to Education userId and candidateId",
                () -> {
                    var index = new Index()
                            .on("userId", Sort.Direction.ASC).on("candidateId", Sort.Direction.ASC).background();
                    mongoTemplate.indexOps(Education.class).ensureIndex(index);
                    }
                );
        doChangeSet(
                "changeset-002",
                "tim.schimandle",
                "add index to candidateId",
                () -> {
                    var index = new Index()
                            .on("candidateId", Sort.Direction.ASC).background();
                    mongoTemplate.indexOps(Education.class).ensureIndex(index);
                }
        );
        doChangeSet(
                "changeset-003",
                "tim.schimandle",
                "add index to userId",
                () -> {
                    var index = new Index()
                            .on("userId", Sort.Direction.ASC).background();
                    mongoTemplate.indexOps(Education.class).ensureIndex(index);
                }
        );
    }

    private void doChangeSet(String id, String author, String description, Runnable change) {
        var changeSet = new ChangeSet();
        changeSet.setId(id);
        changeSet.setAuthor(author);
        changeSet.setDescription(description);
        changeSet.setCreatedDate(System.currentTimeMillis());
        var cs = changeSetRepository.findById(id);

        if (
                cs.isEmpty() || //change set doesn't exist
                (!cs.get().isInProgress() && !cs.get().isCompleted()) //change is not complete and not in progress
        ) {
            try {
                changeSetRepository.save(changeSet); //starts work
                //do the work
                change.run();

                //update the changeset
                var changeSet1 = changeSetRepository.findById(changeSet.getId());
                changeSet1.ifPresent(c -> {
                    c.setInProgress(false);
                    c.setCompleted(true);
                    changeSetRepository.save(c);
                });
            } catch (Exception e) {
                //fail the changeset if there's an issue
                changeSet.setInProgress(false);
                changeSet.setCompleted(false);
                changeSet.setDescription(e.getMessage());
                changeSetRepository.save(changeSet);
            }
        } else {
            //skip, already applied
        }
    }
}
