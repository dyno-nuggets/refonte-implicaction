package com.dynonuggets.refonteimplicaction.repository;

import com.dynonuggets.refonteimplicaction.model.Relation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// supprime les warnings sur les noms des méthodes ne respectant pas la convention de nommage (cf named queries)
@SuppressWarnings("squid:S00100")
public interface RelationRepository extends JpaRepository<Relation, Long> {

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END " +
            "FROM Relation r " +
            "WHERE (r.sender.id = ?1 AND r.receiver.id = ?2) OR (r.sender.id = ?2 AND r.receiver.id = ?1)")
    boolean isInRelation(@Param("user1") Long userId1, Long userId2);

    List<Relation> findAllBySender_IdAndConfirmedAtIsNotNull(Long userId, Pageable pageable);

    List<Relation> findAllBySender_IdAndConfirmedAtIsNull(Long userId, Pageable pageable);

    List<Relation> findAllByReceiver_IdAndConfirmedAtIsNull(Long userId, Pageable pageable);
}
