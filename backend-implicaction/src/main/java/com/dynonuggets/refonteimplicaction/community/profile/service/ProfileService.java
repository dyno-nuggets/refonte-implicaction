package com.dynonuggets.refonteimplicaction.community.profile.service;

import com.dynonuggets.refonteimplicaction.auth.service.AuthService;
import com.dynonuggets.refonteimplicaction.community.profile.adapter.ProfileAdapter;
import com.dynonuggets.refonteimplicaction.community.profile.domain.model.Profile;
import com.dynonuggets.refonteimplicaction.community.profile.domain.repository.ProfileRepository;
import com.dynonuggets.refonteimplicaction.community.profile.dto.ProfileDto;
import com.dynonuggets.refonteimplicaction.community.profile.dto.ProfileUpdateRequest;
import com.dynonuggets.refonteimplicaction.core.error.EntityNotFoundException;
import com.dynonuggets.refonteimplicaction.model.FileModel;
import com.dynonuggets.refonteimplicaction.service.CloudService;
import com.dynonuggets.refonteimplicaction.user.domain.model.UserModel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.dynonuggets.refonteimplicaction.community.profile.error.ProfileErrorResult.PROFILE_NOT_FOUND;

@Service
@AllArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileAdapter profileAdapter;
    private final AuthService authService;
    private final CloudService cloudService;

    /**
     * @param username le nom d’utilisateur associé au profil
     * @return le profil utilisateur s’il existe
     * @throws EntityNotFoundException si le profil utilisateur n’existe pas
     */
    public Profile getByUsernameIfExistsAndUserEnabled(final String username) {
        return profileRepository.findByUser_UsernameAndUser_EnabledTrue(username)
                .orElseThrow(() -> new EntityNotFoundException(PROFILE_NOT_FOUND, username));
    }

    /**
     * @param pageRequest contient les critères de pagination et de tri
     * @return la liste paginée des ProfileDto
     */
    public Page<ProfileDto> getAllProfiles(final Pageable pageRequest) {
        return profileRepository.findAll(pageRequest)
                .map(profileAdapter::toDto);
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
        return profileAdapter.toDto(getByUsernameIfExistsAndUserEnabled(username));
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

        final Profile profile = getByUsernameIfExistsAndUserEnabled(requestUsername);

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

        return profileAdapter.toDto(profileRepository.save(profile));
    }

    /**
     * Met à jour l’avatar de l’utilisateur username
     *
     * @param file     fichier image de l'avatar
     * @param username nom de l’utilisateur à mettre à jour
     * @return le profil utilisateur modifié
     */
    @Transactional
    public ProfileDto updateAvatar(@NonNull final MultipartFile file, @NonNull final String username) {
        authService.verifyAccessIsGranted(username);

        final Profile profile = getByUsernameIfExistsAndUserEnabled(username);
        final FileModel avatar = cloudService.uploadImage(file);
        profile.setAvatar(avatar);

        return profileAdapter.toDto(profileRepository.save(profile));
    }
}
