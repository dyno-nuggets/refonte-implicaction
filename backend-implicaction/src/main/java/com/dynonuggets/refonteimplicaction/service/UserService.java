package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.adapter.GroupAdapter;
import com.dynonuggets.refonteimplicaction.adapter.UserAdapter;
import com.dynonuggets.refonteimplicaction.dto.GroupDto;
import com.dynonuggets.refonteimplicaction.dto.RelationTypeEnum;
import com.dynonuggets.refonteimplicaction.dto.UserDto;
import com.dynonuggets.refonteimplicaction.exception.UserNotFoundException;
import com.dynonuggets.refonteimplicaction.model.FileModel;
import com.dynonuggets.refonteimplicaction.model.Relation;
import com.dynonuggets.refonteimplicaction.model.User;
import com.dynonuggets.refonteimplicaction.repository.FileRepository;
import com.dynonuggets.refonteimplicaction.repository.RelationRepository;
import com.dynonuggets.refonteimplicaction.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static com.dynonuggets.refonteimplicaction.utils.Message.USER_NOT_FOUND_MESSAGE;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RelationRepository relationRepository;
    private final AuthService authService;
    private final UserAdapter userAdapter;
    private final CloudService cloudService;
    private final FileRepository fileRepository;
    private final GroupAdapter groupAdapter;

    /**
     * @return la liste paginée de tous les utilisateurs
     */
    @Transactional(readOnly = true)
    public Page<UserDto> getAll(final Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userAdapter::toDto);
    }

    /**
     * @return la liste paginée de tous les utilisateurs dont l'inscription a été validée
     */
    @Transactional(readOnly = true)
    public Page<UserDto> getAllCommunity(final Pageable pageable) {
        final Long currentUserId = authService.getCurrentUser().getId();

        final Page<UserDto> users = userRepository.findAllByIdNot(pageable, currentUserId)
                .map(userAdapter::toDto);

        final List<Long> userIds = users.map(UserDto::getId)
                .get()
                .collect(toList());
        // on recherche les relations de tous les utilisateurs remontés avec l'utilisateur courant ...
        final List<Relation> relations = relationRepository.findAllRelatedToUserByUserIdIn(currentUserId, userIds);

        // ... et on associe chaque relation avec un statut
        relations.forEach(relation -> users.stream()
                .filter(user -> isSenderOrReceiver(relation, user.getId()) && !currentUserId.equals(user.getId()))
                .findFirst()
                .ifPresent(user -> user.setRelationTypeOfCurrentUser(getRelationType(relation, currentUserId))));
        return users;
    }

    @Transactional
    public UserDto getUserById(final Long userId) {
        return userAdapter.toDto(
                getUserByIdIfExists(userId)
        );
    }

    @Transactional
    public UserDto updateUser(final UserDto userDto) {
        final User user = getUserByIdIfExists(userDto.getId());

        // on attribue les valeurs des champs manquants à notre user présent dans la BD avec la conversion
        // vers le modèle de l'adapter des champs modifiés afin de mettre à jour le user entier directement dans la BD
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setBirthday(userDto.getBirthday());
        user.setHobbies(userDto.getHobbies());
        user.setPresentation(userDto.getPresentation());
        user.setPurpose(userDto.getPurpose());
        user.setContribution(userDto.getContribution());
        user.setExpectation(userDto.getExpectation());

        return userAdapter.toDto(
                userRepository.save(user)
        );
    }

    private boolean isSenderOrReceiver(final Relation relation, final Long userId) {
        return Stream.of(relation.getReceiver(), relation.getSender())
                .map(User::getId)
                .filter(Objects::nonNull)
                .anyMatch(id -> id.equals(userId));
    }

    @Transactional(readOnly = true)
    public Page<UserDto> getAllPendingActivationUsers(final Pageable pageable) {
        return userRepository.findAllByActivatedAtIsNull(pageable)
                .map(userAdapter::toDto);
    }

    private RelationTypeEnum getRelationType(final Relation relation, final Long userId) {
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

    @Transactional
    public UserDto updateImageProfile(final MultipartFile file) {
        final User currentUser = authService.getCurrentUser();
        final FileModel fileModel = cloudService.uploadImage(file);
        final FileModel fileSave = fileRepository.save(fileModel);
        currentUser.setImage(fileSave);

        return userAdapter.toDto(
                userRepository.save(currentUser)
        );
    }

    @Transactional(readOnly = true)
    public List<GroupDto> getUserGroups(final Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE))
                .getGroups()
                .stream()
                .map(groupAdapter::toDto)
                .collect(toList());
    }

    public User getUserByIdIfExists(final Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(format(USER_NOT_FOUND_MESSAGE, userId)));
    }
}
