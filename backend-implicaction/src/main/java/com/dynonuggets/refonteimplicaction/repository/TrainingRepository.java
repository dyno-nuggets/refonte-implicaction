package com.dynonuggets.refonteimplicaction.repository;

import com.dynonuggets.refonteimplicaction.model.Training;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// supprime les warnings sur les noms des méthodes ne respectant pas la convention de nommage (cf named queries)
@SuppressWarnings("squid:S00100")
public interface TrainingRepository extends JpaRepository<Training, Long> {
    List<Training> findAllByUser_Id(Long userId);
}
