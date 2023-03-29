package com.dynonuggets.refonteimplicaction.job.jobapplication.domain.repository;

import com.dynonuggets.refonteimplicaction.job.jobapplication.domain.model.JobApplication;
import com.dynonuggets.refonteimplicaction.user.domain.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// supprime les warnings sur les noms des méthodes ne respectant pas la convention de nommage (cf named queries)
@SuppressWarnings("squid:S00100")
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    Optional<JobApplication> findByJob_IdAndUser_id(long jobId, long userId);

    List<JobApplication> findAllByUserAndArchiveIsFalse(UserModel user);

    List<JobApplication> findAllByJob_IdInAndUser_Id(List<Long> collect, Long userId);
}
