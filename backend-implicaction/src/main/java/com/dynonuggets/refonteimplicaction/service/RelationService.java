package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.adapter.RelationAdapter;
import com.dynonuggets.refonteimplicaction.dto.RelationsDto;
import com.dynonuggets.refonteimplicaction.exception.ImplicactionException;
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

    public List<RelationsDto> getAllForUserId(Pageable pageable, Long userId) {
        final List<Relation> relations = relationRepository.findAllByUserId(userId, pageable);
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
}
