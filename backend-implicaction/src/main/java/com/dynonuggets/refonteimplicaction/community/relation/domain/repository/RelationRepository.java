package com.dynonuggets.refonteimplicaction.community.relation.domain.repository;

import com.dynonuggets.refonteimplicaction.community.relation.domain.model.Relation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// Supprime les warnings sur les noms des méthodes ne respectant pas la convention de nommage (cf. named queries)
@SuppressWarnings("squid:S00100")
public interface RelationRepository extends JpaRepository<Relation, Long> {

    @Query("select case when count (r) > 0 then true else false end " +
            "from Relation r " +
            "where (r.sender.user.username = ?1 and r.receiver.user.username = ?2) " +
            "or (r.sender.user.username = ?2 and r.receiver.user.username = ?1)")
    boolean areInRelation(String userId1, String userId2);

    @Query("select r from Relation r " +
            "where (r.sender.user.username = ?1 or r.receiver.user.username = ?1) " +
            "and r.confirmedAt is not null")
    Page<Relation> findAllByUser_UsernameAndConfirmedAtIsNotNull(String username, Pageable pageable);

    Page<Relation> findAllByReceiver_User_UsernameAndConfirmedAtIsNull(String username, Pageable pageable);

    Page<Relation> findAllBySender_User_UsernameAndConfirmedAtIsNull(String username, Pageable pageable);

    @Query("select r " +
            "from Relation r " +
            "where r.sender.user.username in ?2 and r.receiver.user.username = ?1 " +
            "or (r.receiver.user.username in ?2 and r.sender.user.username = ?1)")
    List<Relation> findAllRelationByUsernameWhereUserListAreSenderOrReceiver(String username, List<String> usernames, Pageable pageable);
}
