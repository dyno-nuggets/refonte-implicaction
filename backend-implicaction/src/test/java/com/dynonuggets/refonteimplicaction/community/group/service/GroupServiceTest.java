package com.dynonuggets.refonteimplicaction.community.group.service;

import com.dynonuggets.refonteimplicaction.auth.service.AuthService;
import com.dynonuggets.refonteimplicaction.community.group.domain.model.GroupModel;
import com.dynonuggets.refonteimplicaction.community.group.domain.repository.GroupRepository;
import com.dynonuggets.refonteimplicaction.community.group.dto.CreateGroupRequest;
import com.dynonuggets.refonteimplicaction.community.group.dto.GroupDto;
import com.dynonuggets.refonteimplicaction.community.group.error.GroupException;
import com.dynonuggets.refonteimplicaction.community.group.mapper.GroupMapper;
import com.dynonuggets.refonteimplicaction.community.profile.domain.model.ProfileModel;
import com.dynonuggets.refonteimplicaction.community.profile.service.ProfileService;
import com.dynonuggets.refonteimplicaction.core.error.EntityNotFoundException;
import com.dynonuggets.refonteimplicaction.core.error.ImplicactionException;
import com.dynonuggets.refonteimplicaction.filemanagement.model.domain.FileModel;
import com.dynonuggets.refonteimplicaction.filemanagement.service.FileService;
import com.dynonuggets.refonteimplicaction.filemanagement.service.impl.S3CloudServiceImpl;
import com.dynonuggets.refonteimplicaction.user.domain.model.UserModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;
import java.util.Optional;

import static com.dynonuggets.refonteimplicaction.community.group.error.GroupErrorResult.GROUP_NOT_FOUND;
import static com.dynonuggets.refonteimplicaction.community.group.error.GroupErrorResult.USER_ALREADY_SUBSCRIBED_TO_GROUP;
import static com.dynonuggets.refonteimplicaction.community.group.utils.GroupTestUtils.generateRandomGroup;
import static com.dynonuggets.refonteimplicaction.user.domain.enums.RoleEnum.PREMIUM;
import static com.dynonuggets.refonteimplicaction.user.utils.UserTestUtils.generateRandomUser;
import static com.dynonuggets.refonteimplicaction.utils.AssertionUtils.assertImplicactionException;
import static java.time.Instant.now;
import static java.util.List.of;
import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {

    @Mock
    GroupRepository groupRepository;
    @Mock
    GroupMapper groupMapper;
    @Mock
    AuthService authService;
    @Mock
    S3CloudServiceImpl cloudService;
    @Mock
    FileService fileService;
    @Mock
    ProfileService profileService;
    @InjectMocks
    GroupService groupService;

    @Captor
    private ArgumentCaptor<GroupModel> argumentCaptor;

    @Nested
    @DisplayName("# createGroup")
    class CreateGroupTest {
        @Test
        @DisplayName("doit créer un groupe quand on soumet une image")
        void should_create_group_when_image_is_submitted() {
            // given
            final MockMultipartFile mockedImage = new MockMultipartFile("user-file", "test.jpg", "image/jpeg", "test data".getBytes());
            final CreateGroupRequest createGroupRequest = CreateGroupRequest.builder().name("coucou subreddit").description("Elle est super bien ma description").build();
            final FileModel fileModel = FileModel.builder().contentType(mockedImage.getContentType()).url("http://url.com").filename(mockedImage.getOriginalFilename()).build();
            final UserModel currentUser = generateRandomUser(of(PREMIUM), true);
            final String imageUrl = "http://localhost/imapge.png";
            final String username = currentUser.getUsername();
            final ProfileModel profile = ProfileModel.builder().user(currentUser).build();
            final GroupModel expectedGroup = GroupModel.builder().id(12L).name(createGroupRequest.getName()).description(createGroupRequest.getDescription()).creator(profile).createdAt(now()).imageUrl(imageUrl).enabled(false).build();
            given(authService.getCurrentUser()).willReturn(currentUser);
            given(profileService.getByUsernameIfExistsAndUserEnabled(username)).willReturn(profile);
            given(cloudService.uploadImage(mockedImage)).willReturn(fileModel);
            given(fileService.save(fileModel)).willReturn(imageUrl);
            given(groupRepository.save(any(GroupModel.class))).willReturn(expectedGroup);

            // when
            groupService.createGroup(createGroupRequest, mockedImage);

            // then
            verify(profileService, times(1)).getByUsernameIfExistsAndUserEnabled(username);
            verify(cloudService, times(1)).uploadImage(mockedImage);
            verify(fileService, times(1)).save(any(FileModel.class));
            verify(groupMapper, times(1)).toDto(any(GroupModel.class));
            verify(groupRepository, times(1)).save(argumentCaptor.capture());
            final GroupModel captorValue = argumentCaptor.getValue();
            assertThat(captorValue).usingRecursiveComparison().ignoringFields("id", "createdAt").isEqualTo(expectedGroup);
            assertThat(captorValue.getCreatedAt()).isAfterOrEqualTo(expectedGroup.getCreatedAt());
            assertThat(captorValue.getCreatedAt()).isBeforeOrEqualTo(now());
            assertThat(captorValue.getCreator().getUser().getUsername()).isEqualTo(username);
        }

        @Test
        @DisplayName("doit créer un groupe quand on ne soumet pas d'image")
        void should_create_group_when_no_image_is_submitted() {
            // given
            final CreateGroupRequest createGroupRequest = CreateGroupRequest.builder().name("coucou subreddit").description("Elle est super bien ma description").build();
            final UserModel currentUser = generateRandomUser(of(PREMIUM), true);
            final String username = currentUser.getUsername();
            final ProfileModel profile = ProfileModel.builder().user(currentUser).build();
            final GroupModel expectedGroup = GroupModel.builder().id(12L).name(createGroupRequest.getName()).description(createGroupRequest.getDescription()).creator(profile).createdAt(now()).enabled(false).build();
            given(authService.getCurrentUser()).willReturn(currentUser);
            given(profileService.getByUsernameIfExistsAndUserEnabled(username)).willReturn(profile);
            given(groupRepository.save(any(GroupModel.class))).willReturn(expectedGroup);

            // when
            groupService.createGroup(createGroupRequest, null);

            // then
            verifyNoInteractions(cloudService);
            verifyNoInteractions(fileService);
            verify(profileService, times(1)).getByUsernameIfExistsAndUserEnabled(username);
            verify(groupMapper, times(1)).toDto(any(GroupModel.class));
            verify(groupRepository, times(1)).save(argumentCaptor.capture());
            final GroupModel captorValue = argumentCaptor.getValue();
            assertThat(captorValue).usingRecursiveComparison().ignoringFields("id", "createdAt").isEqualTo(expectedGroup);
            assertThat(captorValue.getCreatedAt()).isAfterOrEqualTo(expectedGroup.getCreatedAt());
            assertThat(captorValue.getCreatedAt()).isBeforeOrEqualTo(now());
            assertThat(captorValue.getCreator().getUser().getUsername()).isEqualTo(username);
        }
    }

    @Nested
    @DisplayName("# getAllEnabledGroups")
    class GetAllEnabledGroupsTests {
        @Test
        @DisplayName("doit lister tous les groupes 'enabled'")
        void should_list_all_enabled_groups() {
            // given
            final List<GroupModel> groups = of(
                    generateRandomGroup(true),
                    generateRandomGroup(true),
                    generateRandomGroup(true)
            );
            final int size = groups.size();
            final Pageable unpaged = Pageable.unpaged();
            given(groupRepository.findAllByEnabled(unpaged, true)).willReturn(new PageImpl<>(groups));

            // when
            final Page<GroupDto> actuals = groupService.getAllEnabledGroups(unpaged);

            // then
            assertThat(actuals.getTotalElements()).isEqualTo(size);
            verify(groupRepository, times(1)).findAllByEnabled(unpaged, true);
            verify(groupMapper, times(size)).toDto(any(GroupModel.class));
        }
    }

    @Nested
    @DisplayName("# subscribeGroup")
    class SubscribeGroupTests {
        @Test
        @DisplayName("doit sauvegarder le nouvel utilisateur dans le groupe qd il n'en fait pas déjà partie")
        void should_add_user_to_group_when_group_exists_and_user_is_not_already_member_of() {
            // given
            final long groupId = 12L;
            final UserModel currentUser = UserModel.builder().id(12L).username("username").build();
            final ProfileModel profile = ProfileModel.builder().id(currentUser.getId()).user(currentUser).build();
            given(authService.getCurrentUser()).willReturn(currentUser);
            given(profileService.getByUsernameIfExistsAndUserEnabled(currentUser.getUsername())).willReturn(profile);
            given(groupRepository.findByIdAndEnabledTrue(groupId)).willReturn(Optional.of(generateRandomGroup(true)));

            // when
            groupService.subscribeGroup(groupId);

            // then
            verify(authService, times(1)).getCurrentUser();
            verify(profileService, times(1)).getByUsernameIfExistsAndUserEnabled(currentUser.getUsername());
            verify(groupRepository, times(1)).findByIdAndEnabledTrue(groupId);
            verify(groupRepository, times(1)).save(argumentCaptor.capture());
            final GroupModel groupModel = argumentCaptor.getValue();
            assertThat(groupModel.getProfiles()).contains(profile);
        }

        @Test
        @DisplayName("doit lancer une exception si l'utilisateur fait déjà partie du groupe")
        void should_throw_exception_when_user_is_already_member_of_group() {
            // given
            final long groupId = 12L;
            final UserModel currentUser = UserModel.builder().id(12L).username("username").build();
            final ProfileModel profile = ProfileModel.builder().id(currentUser.getId()).user(currentUser).build();
            final GroupModel group = generateRandomGroup(true);
            group.getProfiles().add(profile);
            given(authService.getCurrentUser()).willReturn(currentUser);
            given(profileService.getByUsernameIfExistsAndUserEnabled(currentUser.getUsername())).willReturn(profile);
            given(groupRepository.findByIdAndEnabledTrue(groupId)).willReturn(Optional.of(group));

            // when
            final ImplicactionException e = Assertions.assertThrows(ImplicactionException.class, () -> groupService.subscribeGroup(groupId));

            // then
            assertImplicactionException(e, GroupException.class, USER_ALREADY_SUBSCRIBED_TO_GROUP, group.getName());
            verify(authService, times(1)).getCurrentUser();
            verify(profileService, times(1)).getByUsernameIfExistsAndUserEnabled(currentUser.getUsername());
            verify(groupRepository, times(1)).findByIdAndEnabledTrue(groupId);
            verify(groupRepository, never()).save(any(GroupModel.class));
        }

        @Test
        @DisplayName("doit lancer une exception quand le groupe n'existe pas ou n'est pas 'enabled'")
        void should_throw_exception_when_group_not_exists_or_is_not_enabled() {
            // given
            final long groupId = 12L;
            final UserModel currentUser = UserModel.builder().id(12L).username("username").build();
            final ProfileModel profile = ProfileModel.builder().id(currentUser.getId()).user(currentUser).build();
            given(authService.getCurrentUser()).willReturn(currentUser);
            given(profileService.getByUsernameIfExistsAndUserEnabled(currentUser.getUsername())).willReturn(profile);
            given(groupRepository.findByIdAndEnabledTrue(groupId)).willReturn(empty());

            // when
            final ImplicactionException e = Assertions.assertThrows(ImplicactionException.class, () -> groupService.subscribeGroup(groupId));

            // then
            assertImplicactionException(e, EntityNotFoundException.class, GROUP_NOT_FOUND, String.valueOf(groupId));
            verify(authService, times(1)).getCurrentUser();
            verify(profileService, times(1)).getByUsernameIfExistsAndUserEnabled(currentUser.getUsername());
            verify(groupRepository, times(1)).findByIdAndEnabledTrue(groupId);
            verify(groupRepository, never()).save(any(GroupModel.class));

        }
    }

    @Nested
    @DisplayName("# enableGroup")
    class EnableGroupTests {
        @Test
        @DisplayName("doit 'enable' le groupe quand il existe")
        void should_enable_group_when_exists() {
            // given
            final GroupModel groupModel = generateRandomGroup(false);
            given(groupRepository.findById(groupModel.getId())).willReturn(Optional.of(groupModel));
            given(groupRepository.save(groupModel)).willReturn(groupModel);

            // when
            groupService.enableGroup(groupModel.getId());

            // then
            verify(groupRepository, times(1)).findById(groupModel.getId());
            verify(groupMapper, times(1)).toDto(any(GroupModel.class));
            verify(groupRepository).save(argumentCaptor.capture());
            final GroupModel actualGroup = argumentCaptor.getValue();
            assertThat(actualGroup.isEnabled()).isTrue();
        }
    }

    @Nested
    @DisplayName("# getByIdIfExists")
    class GetByIdIfExistsTests {
        @ParameterizedTest()
        @ValueSource(booleans = {true, false})
        @DisplayName("doit retourner un groupe quand il existe")
        void should_return_group_when_exists(final boolean isEnable) {
            final GroupModel expectedGroup = generateRandomGroup(isEnable);
            given(groupRepository.findById(expectedGroup.getId())).willReturn(Optional.of(expectedGroup));

            // when
            final GroupModel actualGroup = groupService.getByIdIfExists(expectedGroup.getId());

            // then
            assertThat(actualGroup)
                    .usingRecursiveComparison()
                    .isEqualTo(expectedGroup);
        }


        @Test()
        @DisplayName("doit lancer une exception quand le groupe n'existe")
        void should_throw_exception_when_not_exists() {
            // given
            final long groupId = 12L;
            given(groupRepository.findById(groupId)).willReturn(Optional.empty());

            // when
            final ImplicactionException e = Assertions.assertThrows(ImplicactionException.class, () -> groupService.getByIdIfExists(groupId));

            // then
            assertImplicactionException(e, EntityNotFoundException.class, GROUP_NOT_FOUND, String.valueOf(groupId));
        }
    }

    @Nested
    @DisplayName("# getAllPendingGroups")
    class GetAllPendingGroupsTests {
        @Test
        @DisplayName("doit lister tous les groupes non 'enabled'")
        void should_list_all_enabled_groups() {
            // given
            final List<GroupModel> groups = of(
                    generateRandomGroup(false),
                    generateRandomGroup(false),
                    generateRandomGroup(false)
            );
            final int size = groups.size();
            final Pageable unpaged = Pageable.unpaged();
            given(groupRepository.findAllByEnabled(unpaged, false)).willReturn(new PageImpl<>(groups));

            // when
            final Page<GroupDto> actuals = groupService.getAllPendingGroups(unpaged);

            // then
            assertThat(actuals.getTotalElements()).isEqualTo(size);
            verify(groupRepository, times(1)).findAllByEnabled(unpaged, false);
            verify(groupMapper, times(size)).toDto(any(GroupModel.class));
        }
    }
}
