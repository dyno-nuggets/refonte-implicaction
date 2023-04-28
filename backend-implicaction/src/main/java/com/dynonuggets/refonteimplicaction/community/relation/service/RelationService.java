package com.dynonuggets.refonteimplicaction.community.relation.service;

import com.dynonuggets.refonteimplicaction.auth.service.AuthService;
import com.dynonuggets.refonteimplicaction.community.profile.domain.model.ProfileModel;
import com.dynonuggets.refonteimplicaction.community.profile.service.ProfileService;
import com.dynonuggets.refonteimplicaction.community.relation.domain.model.RelationModel;
import com.dynonuggets.refonteimplicaction.community.relation.domain.repository.RelationRepository;
import com.dynonuggets.refonteimplicaction.community.relation.dto.RelationsDto;
import com.dynonuggets.refonteimplicaction.community.relation.error.RelationException;
import com.dynonuggets.refonteimplicaction.community.relation.mapper.RelationMapper;
import com.dynonuggets.refonteimplicaction.core.domain.model.UserModel;
import com.dynonuggets.refonteimplicaction.core.error.CoreException;
import com.dynonuggets.refonteimplicaction.core.error.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import static com.dynonuggets.refonteimplicaction.community.relation.error.RelationErrorResult.*;
import static com.dynonuggets.refonteimplicaction.core.error.CoreErrorResult.OPERATION_NOT_PERMITTED;
import static com.dynonuggets.refonteimplicaction.core.utils.AppUtils.callIfNotNull;
import static java.time.Instant.now;
import static java.util.stream.Stream.of;

@Service
@AllArgsConstructor
public class RelationService {

    private final ProfileService profileService;
    private final RelationRepository relationRepository;
    private final RelationMapper relationMapper;
    private final AuthService authService;

    /**
     * Crée une nouvelle relation entre le senderId et le receiverName
     *
     * @return la nouvelle relation créée avec confirmedAt à null
     * @throws RelationException <li>si le sender et le receiver sont les mêmes</li>
     *                           <li>l’un des deux utilisateurs n'existe pas</li>
     *                           <li>les 2 utilisateurs sont déjà en relation</li>
     */
    public RelationsDto requestRelation(final String senderName, final String receiverName) {
        if (StringUtils.equals(senderName, receiverName)) {
            throw new RelationException(SENDER_EQUALS_RECEIVER);
        }

        if (!StringUtils.equals(senderName, callIfNotNull(authService.getCurrentUser(), UserModel::getUsername))) {
            throw new CoreException(OPERATION_NOT_PERMITTED);
        }

        final ProfileModel sender = profileService.getByUsernameIfExistsAndUserEnabled(senderName);
        final ProfileModel receiver = profileService.getByUsernameIfExistsAndUserEnabled(receiverName);

        if (relationRepository.areInRelation(senderName, receiverName)) {
            throw new RelationException(RELATION_ALREADY_EXISTS);
        }

        final RelationModel relation = RelationModel.builder().sentAt(now()).sender(sender).receiver(receiver).build();
        final RelationModel save = relationRepository.save(relation);
        return relationMapper.toDto(save);
    }

    /**
     * Supprime la relation dont l’id est fournie si elle existe et si l’utilisateur courant en fait partie
     *
     * @param relationId l'id de la relation à supprimer
     * @throws RelationException si la relation n’existe pas ou si l’utilisateur n’est pas autorisé
     */
    public void removeRelation(final Long relationId) {
        final RelationModel relation = getRelationIfExists(relationId);
        verifyThatCurrentUserCanEditRelation(relation);

        relationRepository.delete(relation);
    }

    /**
     * Confirme la relation dont l’id est fourni
     *
     * @param relationId l'id de la relation à confirmer
     * @return la relation entre senderName et receiverName
     * @throws RelationException si la relation n’existe pas ou si l’utilisateur n’est pas autorisé
     */
    public RelationsDto confirmRelation(final Long relationId) {
        final RelationModel relation = getRelationIfExists(relationId);

        verifyThatCurrentUserCanEditRelation(relation);

        relation.setConfirmedAt(now());
        final RelationModel relationUpdate = relationRepository.save(relation);
        return relationMapper.toDto(relationUpdate);
    }

    private RelationModel getRelationIfExists(final Long relationId) {
        return relationRepository.findById(relationId)
                .orElseThrow(() -> new EntityNotFoundException(RELATION_NOT_FOUND));
    }

    private void verifyThatCurrentUserCanEditRelation(final RelationModel relation) {
        final String currentUsername = callIfNotNull(authService.getCurrentUser(), UserModel::getUsername);

        if (of(relation.getSender().getUser().getUsername(), relation.getReceiver().getUser().getUsername())
                .noneMatch(username -> username.equals(currentUsername))) {
            throw new CoreException(OPERATION_NOT_PERMITTED);
        }
    }
}
