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
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class RelationService {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final RelationRepository relationRepository;
    private final RelationAdapter relationAdapter;

    public RelationsDto requestRelation(Long receiverId) {
        final User sender = authService.getCurrentUser();
        final User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new UserNotFoundException("No user found with id " + receiverId));

        if (relationRepository.isInRelation(sender.getId(), receiverId)) {
            throw new ImplicactionException("déjà en relation");
        }

        final Relation relation = Relation.builder()
                .date(LocalDate.now())
                .sender(sender)
                .receiver(receiver)
                .build();
        final Relation save = relationRepository.save(relation);
        return relationAdapter.toDto(save);
    }
}
