package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.adapter.GroupAdapter;
import com.dynonuggets.refonteimplicaction.adapter.UserAdapter;
import com.dynonuggets.refonteimplicaction.dto.GroupDto;
import com.dynonuggets.refonteimplicaction.dto.RelationTypeEnum;
import com.dynonuggets.refonteimplicaction.dto.UserDto;
import com.dynonuggets.refonteimplicaction.exception.UserNotFoundException;
import com.dynonuggets.refonteimplicaction.model.FileModel;
import com.dynonuggets.refonteimplicaction.model.Group;
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
    private GroupAdapter groupAdapter;

    /**
     * @return la liste paginée de tous les utilisateurs
     */
    @Transactional(readOnly = true)
    public Page<UserDto> getAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(userAdapter::toDto);
    }


    /**
     * @return la liste paginée de tous les utilisateurs dont l'inscription a été validée
     */
    @Transactional(readOnly = true)
    public Page<UserDto> getAllCommunity(Pageable pageable) {
        final Long currentUserId = authService.getCurrentUser().getId();

        final Page<UserDto> users = userRepository.findAllByIdNot(pageable, currentUserId)
                .map(userAdapter::toDto);

        final List<Long> userIds = users.map(UserDto::getId)
                .get()
                .collect(toList());
        // on recherche les relations de tous les utilisateurs remontés avec l'utilisateur courant ...
        List<Relation> relations = relationRepository.findAllRelatedToUserByUserIdIn(currentUserId, userIds);
        // ... et on associe chaque relation avec un statut
        relations.forEach(relation -> users.stream()
                .filter(user -> isSenderOrReceiver(relation, user.getId()) && !currentUserId.equals(user.getId()))
                .findFirst()
                .ifPresent(user -> user.setRelationTypeOfCurrentUser(getRelationType(relation, currentUserId))));
        return users;
    }

    public UserDto getUserById(Long userId) {
        final User user = getUserByIdIfExists(userId);

        return userAdapter.toDto(user);
    }

    public UserDto updateUser(UserDto userDto) {
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

        User userUpdate = userRepository.save(user);
        return userAdapter.toDto(userUpdate);
    }

    private boolean isSenderOrReceiver(Relation relation, Long userId) {
        return userId.equals(relation.getReceiver().getId()) || userId.equals(relation.getSender().getId());
    }

    public Page<UserDto> getAllPendingActivationUsers(Pageable pageable) {
        return userRepository.findAllByActivatedAtIsNull(pageable)
                .map(userAdapter::toDto);
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

    @Transactional
    public UserDto updateImageProfile(MultipartFile file) {
        final User currentUser = authService.getCurrentUser();
        final FileModel fileModel = cloudService.uploadImage(file);
        final FileModel fileSave = fileRepository.save(fileModel);
        currentUser.setImage(fileSave);
        final User save = userRepository.save(currentUser);
        return userAdapter.toDto(save);
    }

    @Transactional(readOnly = true)
    public List<GroupDto> getUserGroups(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE));

        final List<Group> groups = user.getGroups();
        return groups.stream()
                .map(groupAdapter::toDto)
                .collect(toList());
    }

    private User getUserByIdIfExists(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(format(USER_NOT_FOUND_MESSAGE, userId)));
    }
}
