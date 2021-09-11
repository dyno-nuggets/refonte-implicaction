package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.adapter.RelationAdapter;
import com.dynonuggets.refonteimplicaction.dto.RelationsDto;
import com.dynonuggets.refonteimplicaction.exception.ImplicactionException;
import com.dynonuggets.refonteimplicaction.exception.NotFoundException;
import com.dynonuggets.refonteimplicaction.exception.UserNotFoundException;
import com.dynonuggets.refonteimplicaction.model.Relation;
import com.dynonuggets.refonteimplicaction.model.User;
import com.dynonuggets.refonteimplicaction.repository.RelationRepository;
import com.dynonuggets.refonteimplicaction.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class RelationService {

    private final UserRepository userRepository;
    private final RelationRepository relationRepository;
    private final RelationAdapter relationAdapter;

    public RelationsDto requestRelation(Long senderId, Long receiverId) {
        // TODO: gérer avec une exception plus appropriée
        if (senderId.equals(receiverId)) {
            throw new ImplicactionException("Cannot create relation with same user as sender and receiver");
        }

        final User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new UserNotFoundException("No user found with id " + receiverId));
        final User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new UserNotFoundException("No user found with id " + receiverId));

        if (relationRepository.isInRelation(senderId, receiverId)) {
            throw new ImplicactionException("déjà en relation");
        }

        final Relation relation = Relation.builder()
                .sentAt(Instant.now())
                .sender(sender)
                .receiver(receiver)
                .build();
        final Relation save = relationRepository.save(relation);
        return relationAdapter.toDto(save);
    }

    public List<RelationsDto> getAllForUserId(Long userId) {
        final List<Relation> relations = relationRepository.findAllByUserId(userId);
        return relations.stream()
                .map(relationAdapter::toDto)
                .collect(toList());
    }

    public List<RelationsDto> getAllPendingBySenderId(Pageable pageable, Long userId) {
        final List<Relation> relations = relationRepository.findAllBySender_IdAndConfirmedAtIsNull(userId, pageable);
        return relations.stream()
                .map(relationAdapter::toDto)
                .collect(toList());
    }

    public List<RelationsDto> getAllPendingByReceiverId(Pageable pageable, Long userId) {
        final List<Relation> relations = relationRepository.findAllByReceiver_IdAndConfirmedAtIsNull(userId, pageable);
        return relations.stream()
                .map(relationAdapter::toDto)
                .collect(toList());
    }

    /**
     * Supprime la relation entre le sender et le receiver dont les ids sont passé en paramètres
     */
    public void deleteRelation(Long senderId, Long receiverId) {
        Relation relation = relationRepository.findBySender_IdAndReceiver_Id(senderId, receiverId)
                .orElseThrow(() -> new NotFoundException("No relation found from sender " + senderId + " to reveiver " + receiverId));
        relationRepository.delete(relation);
    }

    /**
     * Supprime la relation entre les utilisateurs dont les ids sont passés en paramètres, sans tenir compte de la notion
     * de sender / receiver
     */
    public void cancelRelation(Long userId1, Long userId2) {
        Relation relation = relationRepository.findRelationBetween(userId1, userId2)
                .orElseThrow(() -> new NotFoundException("No relation found between " + userId1 + " and " + userId2));
        relationRepository.delete(relation);
    }

    public RelationsDto acceptRelation(Long relationId) {
        final Relation relation = relationRepository.findRelationByIdAndConfirmedAtIsNull(relationId);
        relation.setConfirmedAt(Instant.now());
        final Relation save = relationRepository.save(relation);
        return relationAdapter.toDto(save);
    }
}
