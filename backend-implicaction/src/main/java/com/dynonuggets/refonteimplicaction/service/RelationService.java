package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.adapter.RelationAdapter;
import com.dynonuggets.refonteimplicaction.adapter.UserAdapter;
import com.dynonuggets.refonteimplicaction.dto.RelationsDto;
import com.dynonuggets.refonteimplicaction.dto.UserDto;
import com.dynonuggets.refonteimplicaction.exception.ImplicactionException;
import com.dynonuggets.refonteimplicaction.exception.NotFoundException;
import com.dynonuggets.refonteimplicaction.exception.UserNotFoundException;
import com.dynonuggets.refonteimplicaction.model.Relation;
import com.dynonuggets.refonteimplicaction.model.User;
import com.dynonuggets.refonteimplicaction.repository.RelationRepository;
import com.dynonuggets.refonteimplicaction.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;

import static com.dynonuggets.refonteimplicaction.dto.RelationTypeEnum.*;

@Service
@AllArgsConstructor
public class RelationService {

    private final UserRepository userRepository;
    private final RelationRepository relationRepository;
    private final RelationAdapter relationAdapter;
    private final UserAdapter userAdapter;

    /**
     * Crée une nouvelle relation entre le senderId et le receiverId
     *
     * @return la nouvelle relation créée avec confirmedAt à null
     * @throws UserNotFoundException si l'un des deux utilisateurs au moins n'existe pas
     * @throws ImplicactionException si sender et receiver correspondent au même utilisateur
     */
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

    /**
     * Supprime la relation entre le sender et le receiver dont les ids sont passé en paramètres
     *
     * @throws NotFoundException si l'un des deux utilisateurs au moins n'existe pas
     */
    public void deleteRelation(Long senderId, Long receiverId) {
        Relation relation = relationRepository.findBySender_IdAndReceiver_Id(senderId, receiverId)
                .orElseThrow(() -> new NotFoundException("No relation found from sender " + senderId + " to reveiver " + receiverId));
        relationRepository.delete(relation);
    }

    /**
     * Supprime la relation entre les utilisateurs dont les ids sont passés en paramètres, sans tenir compte de la notion
     * de sender / receiver
     *
     * @throws NotFoundException si l'un des deux utilisateurs au moins n'existe pas
     */
    public void cancelRelation(Long userId1, Long userId2) {
        Relation relation = relationRepository.findRelationBetween(userId1, userId2)
                .orElseThrow(() -> new NotFoundException("No relation found between " + userId1 + " and " + userId2));
        relationRepository.delete(relation);
    }

    /**
     * Confirme la relation entre les utilisateurs dont les ids sont passés en paramètres
     *
     * @return la relation entre senderId et receiverId
     * @throws NotFoundException si l'un des deux utilisateurs au moins n'existe pas
     */
    public RelationsDto confirmRelation(Long senderId, Long receiverId) {
        Relation relation = relationRepository.findBySender_IdAndReceiver_Id(senderId, receiverId)
                .orElseThrow(() -> new NotFoundException("No relation found from sender " + senderId + " to reveiver " + receiverId));
        relation.setConfirmedAt(Instant.now());
        Relation relationUpdate = relationRepository.save(relation);
        return relationAdapter.toDto(relationUpdate);
    }

    /**
     * @return tous les utilisateurs qui sont amis avec userId
     */
    public Page<UserDto> getAllFriendsByUserId(Long userId, Pageable pageable) {
        Page<Relation> relations = relationRepository.findAllFriendsByUserId(userId, pageable);
        return relations.map(relation -> {
            final User friend = userId.equals(relation.getReceiver().getId()) ? relation.getSender() : relation.getReceiver();
            final UserDto userDto = userAdapter.toDto(friend);
            userDto.setRelationTypeOfCurrentUser(FRIEND);
            return userDto;
        });
    }

    /**
     * @return tous les utilisateurs à qui userId a envoyé une demande d'ami qui n'a pas encore été confirmée
     */
    public Page<UserDto> getSentFriendRequest(Long userId, Pageable pageable) {
        Page<Relation> relations = relationRepository.findAllBySender_IdAndConfirmedAtIsNull(userId, pageable);
        return relations.map(relation -> {
            final UserDto userDto = userAdapter.toDto(relation.getReceiver());
            userDto.setRelationTypeOfCurrentUser(SENDER);
            return userDto;
        });
    }

    /**
     * @return tous les utilisateurs qui ont envoyé une demande d'ami à userId
     */
    public Page<UserDto> getReceivedFriendRequest(Long userId, Pageable pageable) {
        Page<Relation> relations = relationRepository.findAllByReceiver_IdAndConfirmedAtIsNull(userId, pageable);
        return relations.map(relation -> {
            final UserDto userDto = userAdapter.toDto(relation.getSender());
            userDto.setRelationTypeOfCurrentUser(RECEIVER);
            return userDto;
        });
    }
}
