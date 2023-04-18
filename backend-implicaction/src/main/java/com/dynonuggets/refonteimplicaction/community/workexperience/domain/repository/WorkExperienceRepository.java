package com.dynonuggets.refonteimplicaction.community.workexperience.domain.repository;

import com.dynonuggets.refonteimplicaction.community.workexperience.domain.model.WorkExperienceModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// supprime les warnings sur les noms des méthodes ne respectant pas la convention de nommage (cf named queries)
@SuppressWarnings("squid:S00100")
public interface WorkExperienceRepository extends JpaRepository<WorkExperienceModel, Long> {
    List<WorkExperienceModel> findAllByProfile_Id(Long userId);
}
