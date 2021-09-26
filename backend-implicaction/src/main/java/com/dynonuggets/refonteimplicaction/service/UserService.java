package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.adapter.UserAdapter;
import com.dynonuggets.refonteimplicaction.dto.RelationTypeEnum;
import com.dynonuggets.refonteimplicaction.dto.UserDto;
import com.dynonuggets.refonteimplicaction.exception.UserNotFoundException;
import com.dynonuggets.refonteimplicaction.model.JobSeeker;
import com.dynonuggets.refonteimplicaction.model.Relation;
import com.dynonuggets.refonteimplicaction.model.User;
import com.dynonuggets.refonteimplicaction.repository.JobSeekerRepository;
import com.dynonuggets.refonteimplicaction.repository.RelationRepository;
import com.dynonuggets.refonteimplicaction.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RelationRepository relationRepository;
    private final JobSeekerRepository jobSeekerRepository;
    private final AuthService authService;
    private final UserAdapter userAdapter;

    /**
     * @param isCurrentUserRelation recherche les relations avec l'utilisateur courant
     * @return la liste paginée de tous les utilisateurs
     */
    @Transactional(readOnly = true)
    public Page<UserDto> getAll(Pageable pageable, boolean isCurrentUserRelation) {
        final Long currentUserId = authService.getCurrentUser().getId();

        final Page<UserDto> users = userRepository.findAll(pageable)
                .map(userAdapter::toDto);

        if (isCurrentUserRelation) {
            final List<Long> userIds = users.map(UserDto::getId)
                    .get()
                    .collect(Collectors.toList());
            // on recherche les relations de tous les utilisateurs remontés avec l'utilisateur courant ...
            List<Relation> relations = relationRepository.findAllRelatedToUserByUserIdIn(currentUserId, userIds);
            // ... et on associe chaque relation avec un statut
            relations.forEach(relation -> users.stream()
                    .filter(user -> isSenderOrReceiver(relation, user.getId()) && !currentUserId.equals(user.getId()))
                    .findFirst()
                    .ifPresent(user -> user.setRelationTypeOfCurrentUser(getRelationType(relation, currentUserId))));
        }
        return users;
    }

    public UserDto getUserById(Long userId) {
        JobSeeker jobSeeker = jobSeekerRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("No user found with id " + userId));
        return userAdapter.toDto(jobSeeker);
    }

    public UserDto updateByUserId(UserDto userDto, Long id) {
        User databaseUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Impossible de mettre à jour" +
                        " les informations personelles; L'user avec l'id " + id + " n'existe pas."));
        // on attribue les valeurs des champs manquants à notre user présent dans la BD avec la conversion
        // vers le modèle de l'adapter des champs modifiés afin de mettre à jour le user entier directement dans la BD
        databaseUser.setEmail(userDto.getEmail());
        databaseUser.setPhoneNumber(userDto.getPhoneNumber());
        databaseUser.setBirthday(userDto.getBirthday());
        databaseUser.setHobbies(userDto.getHobbies());
        databaseUser.setPresentation(userDto.getPresentation());
        databaseUser.setPurpose(userDto.getPurpose());
        databaseUser.setContribution(userDto.getContribution());
        databaseUser.setExpectation(userDto.getExpectation());

        User userUpdate = userRepository.save(databaseUser);
        return userAdapter.toDto(userUpdate);
    }

    private boolean isSenderOrReceiver(Relation relation, Long userId) {
        return userId.equals(relation.getReceiver().getId()) || userId.equals(relation.getSender().getId());
    }

    private RelationTypeEnum getRelationType(Relation relation, Long userId) {
        if (relation.getConfirmedAt() != null) {
            return RelationTypeEnum.FRIEND;
        }
        if (userId.equals(relation.getReceiver().getId())) {
            return RelationTypeEnum.RECEIVER;
        }
        if (userId.equals(relation.getSender().getId())) {
            return RelationTypeEnum.SENDER;
        }
        return RelationTypeEnum.NONE;
    }
}
