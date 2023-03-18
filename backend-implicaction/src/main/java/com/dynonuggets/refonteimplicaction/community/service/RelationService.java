package com.dynonuggets.refonteimplicaction.community.service;

import com.dynonuggets.refonteimplicaction.auth.service.AuthService;
import com.dynonuggets.refonteimplicaction.community.adapter.RelationAdapter;
import com.dynonuggets.refonteimplicaction.community.domain.model.Profile;
import com.dynonuggets.refonteimplicaction.community.domain.model.Relation;
import com.dynonuggets.refonteimplicaction.community.domain.repository.ProfileRepository;
import com.dynonuggets.refonteimplicaction.community.domain.repository.RelationRepository;
import com.dynonuggets.refonteimplicaction.community.dto.RelationsDto;
import com.dynonuggets.refonteimplicaction.community.error.CommunityException;
import com.dynonuggets.refonteimplicaction.core.error.CoreException;
import com.dynonuggets.refonteimplicaction.core.error.EntityNotFoundException;
import com.dynonuggets.refonteimplicaction.core.user.domain.model.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.dynonuggets.refonteimplicaction.community.dto.RelationTypeEnum.*;
import static com.dynonuggets.refonteimplicaction.community.error.CommunityErrorResult.*;
import static com.dynonuggets.refonteimplicaction.core.error.CoreErrorResult.OPERATION_NOT_PERMITTED;
import static com.dynonuggets.refonteimplicaction.core.util.Utils.callIfNotNull;
import static java.time.Instant.now;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;

@Service
@AllArgsConstructor
public class RelationService {

    private final ProfileService profileService;
    private final ProfileRepository profileRepository;
    private final RelationRepository relationRepository;
    private final RelationAdapter relationAdapter;
    private final AuthService authService;

    /**
     * Crée une nouvelle relation entre le senderId et le receiverName
     *
     * @return la nouvelle relation créée avec confirmedAt à null
     * @throws CommunityException <li>si le sender et le receiver sont les mêmes</li>
     *                            <li>l’un des deux utilisateurs n'existe pas</li>
     *                            <li>les 2 utilisateurs sont déjà en relation</li>
     */
    public RelationsDto requestRelation(final String senderName, final String receiverName) {
        if (senderName.equals(receiverName)) {
            throw new CommunityException(SENDER_EQUALS_RECEIVER);
        }

        final Profile sender = profileService.getByUsernameIfExists(senderName);
        final Profile receiver = profileService.getByUsernameIfExists(receiverName);

        if (relationRepository.areInRelation(senderName, receiverName)) {
            throw new CommunityException(RELATION_ALREADY_EXISTS);
        }

        final Relation relation = Relation.builder().sentAt(now()).sender(sender).receiver(receiver).build();
        final Relation save = relationRepository.save(relation);
        return relationAdapter.toDto(save);
    }

    /**
     * Supprime la relation dont l’id est fournie si elle existe et si l’utilisateur courant en fait partie
     *
     * @param relationId l'id de la relation à supprimer
     * @throws CommunityException si la relation n’existe pas ou si l’utilisateur n’est pas autorisé
     */
    public void removeRelation(final Long relationId) {
        final Relation relation = getRelationIfExists(relationId);
        verifyThatCurrentUserCanEditRelation(relation);

        relationRepository.delete(relation);
    }

    /**
     * Confirme la relation dont l’id est fourni
     *
     * @param relationId l'id de la relation à confirmer
     * @return la relation entre senderName et receiverName
     * @throws CommunityException si la relation n’existe pas ou si l’utilisateur n’est pas autorisé
     */
    public RelationsDto confirmRelation(final Long relationId) {
        final Relation relation = getRelationIfExists(relationId);

        verifyThatCurrentUserCanEditRelation(relation);

        relation.setConfirmedAt(now());
        final Relation relationUpdate = relationRepository.save(relation);
        return relationAdapter.toDto(relationUpdate);
    }

    /**
     * @return tous les utilisateurs qui sont amis avec username
     */
    public Page<RelationsDto> getAllRelationsByUsername(final String username, final Pageable pageable) {
        final Page<Relation> relations = relationRepository.findAllByUser_UsernameAndConfirmedAtIsNotNull(username, pageable);
        return relations.map(relation -> {
            final RelationsDto relationsDto = relationAdapter.toDto(relation);
            relationsDto.setRelationType(FRIEND);
            return relationsDto;
        });
    }

    /**
     * @return tous les utilisateurs à qui username a envoyé une demande d’ami qui n’a pas encore été confirmée
     */
    public Page<RelationsDto> getSentFriendRequest(final String username, final Pageable pageable) {
        authService.verifyAccessIsGranted(username);
        return relationRepository.findAllBySender_User_UsernameAndConfirmedAtIsNull(username, pageable)
                .map(relation -> {
                    final RelationsDto relationsDto = relationAdapter.toDto(relation);
                    relationsDto.setRelationType(SENDER);
                    return relationsDto;
                });
    }

    /**
     * @return tous les utilisateurs qui ont envoyé une demande d’ami à userId
     */
    public Page<RelationsDto> getReceivedFriendRequest(final String username, final Pageable pageable) {
        authService.verifyAccessIsGranted(username);
        final Page<Relation> relations = relationRepository.findAllByReceiver_User_UsernameAndConfirmedAtIsNull(username, pageable);
        return relations.map(relation -> {
            final RelationsDto relationsDto = relationAdapter.toDto(relation);
            relationsDto.setRelationType(RECEIVER);
            return relationsDto;
        });
    }

    /**
     * @return Retourne tous les utilisateurs sous forme de relation
     */
    public Page<RelationsDto> getAllCommunity(final Pageable pageable) {
        final String currentUsername = callIfNotNull(authService.getCurrentUser(), User::getUsername);
        final Page<Profile> allProfiles = profileRepository.findAllByUser_UsernameNot(currentUsername, pageable);
        final List<String> allProfilesUsernames = allProfiles.get().map(p -> p.getUser().getUsername()).collect(toList());
        final List<Relation> currentUsersRelations = relationRepository.findAllRelationByUsernameWhereUserListAreSenderOrReceiver(currentUsername, allProfilesUsernames, pageable);
        return allProfiles
                .map(profile -> {
                    final Relation relation = currentUsersRelations.stream()
                            // On recherche la relation entre l’utilisateur courant et le profil de la liste des profils
                            .filter(r -> isPartOfRelation(profile.getUser().getUsername(), r))
                            .findAny()
                            // Si elle n’existe pas, on crée une relation dont le profil est receiver et un sender null.
                            .orElse(Relation.builder().receiver(profile).build());

                    final RelationsDto relationsDto = relationAdapter.toDto(relation);
                    if (relationsDto.getConfirmedAt() != null) {
                        relationsDto.setRelationType(FRIEND);
                    } else if (relationsDto.getSender() == null) {
                        relationsDto.setRelationType(NONE);
                    } else {
                        relationsDto.setRelationType(relationsDto.getSender().getUsername().equals(currentUsername) ? SENDER : RECEIVER);
                    }

                    return relationsDto;
                });
    }

    private static boolean isPartOfRelation(final String username, final Relation r) {
        return isSender(username, r) || isReceiver(username, r);
    }

    private static boolean isReceiver(final String username, final Relation r) {
        if (r.getReceiver() == null) {
            return false;
        }

        return r.getReceiver().getUser().getUsername().equals(username);
    }

    private static boolean isSender(final String username, final Relation r) {
        if (r.getSender() == null) {
            return false;
        }

        return r.getSender().getUser().getUsername().equals(username);
    }

    private Relation getRelationIfExists(final Long relationId) {
        return relationRepository.findById(relationId)
                .orElseThrow(() -> new EntityNotFoundException(RELATION_NOT_FOUND));
    }

    private void verifyThatCurrentUserCanEditRelation(final Relation relation) {
        final String currentUsername = callIfNotNull(authService.getCurrentUser(), User::getUsername);

        if (of(relation.getSender().getUser().getUsername(), relation.getReceiver().getUser().getUsername())
                .noneMatch(username -> username.equals(currentUsername))) {
            throw new CoreException(OPERATION_NOT_PERMITTED);
        }
    }
}
