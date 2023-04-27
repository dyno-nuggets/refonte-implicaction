package com.dynonuggets.refonteimplicaction.community.profile.service;

import com.dynonuggets.refonteimplicaction.auth.service.AuthService;
import com.dynonuggets.refonteimplicaction.community.profile.domain.model.ProfileModel;
import com.dynonuggets.refonteimplicaction.community.profile.domain.repository.ProfileRepository;
import com.dynonuggets.refonteimplicaction.community.profile.dto.ProfileDto;
import com.dynonuggets.refonteimplicaction.community.profile.dto.ProfileUpdateRequest;
import com.dynonuggets.refonteimplicaction.community.profile.mapper.ProfileMapper;
import com.dynonuggets.refonteimplicaction.core.domain.model.UserModel;
import com.dynonuggets.refonteimplicaction.community.relation.domain.repository.RelationRepository;
import com.dynonuggets.refonteimplicaction.community.relation.dto.RelationsDto;
import com.dynonuggets.refonteimplicaction.community.relation.mapper.RelationMapper;
import com.dynonuggets.refonteimplicaction.core.error.EntityNotFoundException;
import com.dynonuggets.refonteimplicaction.core.error.TechnicalException;
import com.dynonuggets.refonteimplicaction.core.service.UserService;
import com.dynonuggets.refonteimplicaction.filemanagement.service.CloudService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.dynonuggets.refonteimplicaction.community.profile.error.ProfileErrorResult.PROFILE_NOT_FOUND;
import static com.dynonuggets.refonteimplicaction.community.profile.utils.ProfileMessages.PROFILE_ALREADY_EXISTS_MESSAGE;
import static com.dynonuggets.refonteimplicaction.community.profile.utils.ProfileMessages.PROFILE_CREATED_SUCCESSFULLY_MESSAGE;
import static java.lang.String.format;

@Slf4j
@Service
@AllArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserService userService;
    private final ProfileMapper profileMapper;
    private final AuthService authService;
    private final CloudService cloudService;
    private final RelationRepository relationRepository;
    private final RelationMapper relationMapper;

    /**
     * crée un nouveau profil si l’utilisateur existe
     *
     * @throws TechnicalException      si le profil existe déjà
     * @throws EntityNotFoundException si l’utilisateur n’existe pas
     */
    @Transactional
    public void createProfile(final String username) {
        final UserModel user = userService.getUserByUsernameIfExists(username);
        profileRepository.findByUser_Username(username)
                .ifPresentOrElse(
                        profileModel -> {
                            final String message = format(PROFILE_ALREADY_EXISTS_MESSAGE, username);
                            log.error(message);
                            throw new TechnicalException(message);
                        },
                        () -> {
                            log.info(PROFILE_CREATED_SUCCESSFULLY_MESSAGE);
                            profileRepository.save(ProfileModel.builder().user(user).build());
                        }
                );
    }

    /**
     * @param username le nom d’utilisateur associé au profil
     * @return le profil utilisateur s’il existe
     * @throws EntityNotFoundException si le profil utilisateur n’existe pas
     */
    public ProfileModel getByUsernameIfExistsAndUserEnabled(final String username) {
        return profileRepository.findByUser_UsernameAndUser_EnabledTrue(username)
                .orElseThrow(() -> new EntityNotFoundException(PROFILE_NOT_FOUND, username));
    }

    /**
     * @param pageRequest contient les critères de pagination et de tri
     * @return la liste paginée des ProfileDto
     */
    public Page<ProfileDto> getAllProfiles(final Pageable pageRequest) {
        final Page<ProfileModel> profilesModels = profileRepository.findAll(pageRequest);

        final String currentUsername = authService.getCurrentUser().getUsername();

        final List<String> allUsernamesWithoutCurrents = profilesModels.stream()
                .map(ProfileModel::getUser)
                .map(UserModel::getUsername)
                .filter(username -> !username.equals(currentUsername))
                .collect(Collectors.toList());

        final Map<String, RelationsDto> relationTypeMap = relationRepository.findAllRelationByUsernameWhereUserListAreSenderOrReceiver(currentUsername, allUsernamesWithoutCurrents, Pageable.unpaged()).stream()
                .collect(Collectors.toMap(relation -> {
                    final String username = relation.getReceiver().getUser().getUsername();
                    return !username.equals(currentUsername) ? username : relation.getSender().getUser().getUsername();
                }, relationMapper::toDto));

        return profilesModels
                .map(profileMapper::toDtoLight)
                .map(dto -> {
                    dto.setRelationWithCurrentUser(relationTypeMap.getOrDefault(dto.getUsername(), null));
                    return dto;
                });
    }

    /**
     * Renvoie le profil s’il existe, sinon, si l’utilisateur existe, le profil est créé
     *
     * @param username le nom d’utilisateur pour lequel on recherche le profil
     * @return le ProfileDto associé au nom d’utilisateur en paramètres s’il existe
     * @throws EntityNotFoundException si aucun utilisateur dont le nom est 'username' n’existe
     */
    @Transactional(readOnly = true)
    public ProfileDto getByUsername(final String username) {
        return profileMapper.toDto(getByUsernameIfExistsAndUserEnabled(username));
    }

    /**
     * Met à jour un profil utilisateur
     *
     * @param updateRequest données de mise à jour de l’utilisateur
     * @return le ProfileDto mis à jour
     */
    @Transactional
    public ProfileDto updateProfile(@NonNull final ProfileUpdateRequest updateRequest) {
        final String requestUsername = updateRequest.getUsername();
        authService.verifyAccessIsGranted(requestUsername);

        final ProfileModel profile = getByUsernameIfExistsAndUserEnabled(requestUsername);

        final UserModel user = profile.getUser();
        user.setFirstname(updateRequest.getFirstname());
        user.setLastname(updateRequest.getLastname());
        user.setEmail(updateRequest.getEmail());
        user.setBirthday(updateRequest.getBirthday());

        profile.setHobbies(updateRequest.getHobbies());
        profile.setPurpose(updateRequest.getPurpose());
        profile.setPresentation(updateRequest.getPresentation());
        profile.setExpectation(updateRequest.getExpectation());
        profile.setContribution(updateRequest.getContribution());
        profile.setPhoneNumber(updateRequest.getPhoneNumber());

        return profileMapper.toDto(profileRepository.save(profile));
    }

    @Transactional(readOnly = true)
    public ProfileModel getCurrentProfile() {
        return getByUsernameIfExistsAndUserEnabled(authService.getCurrentUser().getUsername());
    }

    @Transactional
    public ProfileDto uploadAvatar(@NonNull final MultipartFile file, @NonNull final String username) {
        final ProfileModel profile = getByUsernameIfExistsAndUserEnabled(username);
        final String imageUrl = cloudService.uploadPublicImage(file);
        profile.setImageUrl(imageUrl);
        return profileMapper.toDtoLight(profileRepository.save(profile));
    }
}

