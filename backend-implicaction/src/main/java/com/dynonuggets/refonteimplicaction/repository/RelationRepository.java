package com.dynonuggets.refonteimplicaction.repository;

import com.dynonuggets.refonteimplicaction.model.Relation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

// supprime les warnings sur les noms des méthodes ne respectant pas la convention de nommage (cf named queries)
@SuppressWarnings("squid:S00100")
public interface RelationRepository extends JpaRepository<Relation, Long> {

    @Query("select case when count (r) > 0 then true else false end " +
            "from Relation r " +
            "where (r.sender.id = ?1 and r.receiver.id = ?2) or (r.sender.id = ?2 and r.receiver.id = ?1)")
    boolean isInRelation(@Param("user1") Long userId1, Long userId2);

    @Query("select r from Relation r " +
            "where (r.sender.id = ?1 or r.receiver.id = ?1)")
    List<Relation> findAllByUserId(Long userId);

    @Query("select r from Relation r " +
            "where (r.sender.id = ?1 or r.receiver.id = ?1) " +
            "and r.confirmedAt is not null")
    Page<Relation> findAllFriendsByUserId(Long userId, Pageable pageable);

    Page<Relation> findAllByReceiver_IdAndConfirmedAtIsNull(Long userId, Pageable pageable);

    Page<Relation> findAllBySender_IdAndConfirmedAtIsNull(Long userId, Pageable pageable);

    Optional<Relation> findBySender_IdAndReceiver_Id(Long senderId, Long receiverId);

    @Query("select r " +
            "from Relation r " +
            "where (r.sender.id = ?1 and r.receiver.id = ?2) or (r.sender.id = ?2 and r.receiver.id = ?1)")
    Optional<Relation> findRelationBetween(Long userId1, Long userId2);

    @Query("select r " +
            "from Relation r " +
            "where r.sender.id in ?1 or r.receiver.id in ?1")
    List<Relation> findAllByUserIdIn(List<Long> userIds);

    @Query("select r " +
            "from Relation r " +
            "where (r.sender.id in ?2 and r.receiver.id = ?1) or (r.receiver.id in ?2 and r.sender.id = ?1)")
    List<Relation> findAllRelatedToUserByUserIdIn(Long userId, List<Long> userIds);
}
