package com.dynonuggets.refonteimplicaction.repository;

import com.dynonuggets.refonteimplicaction.model.Relation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RelationRepository extends JpaRepository<Relation, Long> {

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Relation r WHERE (r.sender.id = ?1 and r.receiver.id = ?2) OR (r.sender.id = ?2 and r.receiver.id = ?1)")
    boolean isInRelation(@Param("user1") Long userId1, Long userId2);
}
