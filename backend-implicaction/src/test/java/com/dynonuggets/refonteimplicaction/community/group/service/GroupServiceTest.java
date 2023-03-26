package com.dynonuggets.refonteimplicaction.community.group.service;

import com.dynonuggets.refonteimplicaction.auth.service.AuthService;
import com.dynonuggets.refonteimplicaction.community.group.adapter.GroupAdapter;
import com.dynonuggets.refonteimplicaction.community.group.domain.model.Group;
import com.dynonuggets.refonteimplicaction.community.group.domain.repository.GroupRepository;
import com.dynonuggets.refonteimplicaction.community.group.dto.GroupDto;
import com.dynonuggets.refonteimplicaction.community.profile.domain.model.ProfileModel;
import com.dynonuggets.refonteimplicaction.community.profile.domain.repository.ProfileRepository;
import com.dynonuggets.refonteimplicaction.community.profile.service.ProfileService;
import com.dynonuggets.refonteimplicaction.model.FileModel;
import com.dynonuggets.refonteimplicaction.repository.FileRepository;
import com.dynonuggets.refonteimplicaction.service.impl.S3CloudServiceImpl;
import com.dynonuggets.refonteimplicaction.user.domain.model.UserModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {

    @Mock
    GroupRepository groupRepository;
    @Mock
    GroupAdapter groupAdapter;
    @Mock
    AuthService authService;
    @Mock
    S3CloudServiceImpl cloudService;
    @Mock
    FileRepository fileRepository;
    @Mock
    ProfileRepository profileRepository;
    @Mock
    ProfileService profileService;
    @InjectMocks
    GroupService groupService;

    @Captor
    private ArgumentCaptor<Group> argumentCaptor;

    @Test
    void should_save_subreddit_with_image() {
        // given
        final GroupDto sentDto = GroupDto.builder()
                .name("coucou subreddit")
                .description("Elle est super bien ma description")
                .build();

        final Group sentModel = Group.builder()
                .name("coucou subreddit")
                .description("Elle est super bien ma description")
                .build();

        final Group saveModel = Group.builder()
                .id(123L)
                .name("coucou subreddit")
                .description("Elle est super bien ma description")
                .build();

        final GroupDto expectedDto = GroupDto.builder()
                .id(123L)
                .name("coucou subreddit")
                .description("Elle est super bien ma description")
                .build();

        final UserModel currentUser = UserModel.builder().id(123L).build();

        final FileModel fileModel = FileModel.builder()
                .id(123L)
                .contentType("image/jpeg")
                .url("http://url.com")
                .filename("test.jpg")
                .build();

        final MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "user-file",
                "test.jpg",
                "image/jpeg",
                "test data".getBytes()
        );

        final ProfileModel profile = ProfileModel.builder().user(currentUser).build();

        when(cloudService.uploadImage(any())).thenReturn(fileModel);
        when(fileRepository.save(fileModel)).thenReturn(fileModel);
        when(groupAdapter.toModel(any(), any())).thenReturn(sentModel);
        when(authService.getCurrentUser()).thenReturn(currentUser);
        when(groupRepository.save(any())).thenReturn(saveModel);
        when(groupAdapter.toDto(any())).thenReturn(expectedDto);
        given(profileService.getByUsernameIfExistsAndUserEnabled(any())).willReturn(profile);

        // when
        groupService.save(mockMultipartFile, sentDto);

        // then
        verify(groupRepository, times(1)).save(argumentCaptor.capture());

        final Group value = argumentCaptor.getValue();

        assertThat(value.getId()).isNull();
        assertThat(value.getName()).isEqualTo("coucou subreddit");
        assertThat(value.getDescription()).isEqualTo("Elle est super bien ma description");
        assertThat(value.getImage().getFilename()).isEqualTo("test.jpg");
    }


    @Test
    void should_save_when_no_image() {
        // given
        final GroupDto sentDto = GroupDto.builder()
                .name("coucou subreddit")
                .description("Elle est super bien ma description")
                .build();

        final Group sentModel = Group.builder()
                .name("coucou subreddit")
                .description("Elle est super bien ma description")
                .build();

        final UserModel currentUser = UserModel.builder().id(123L).build();

        final Group saveModel = Group.builder()
                .id(123L)
                .name("coucou subreddit")
                .description("Elle est super bien ma description")
                .build();

        final ProfileModel profile = ProfileModel.builder().user(currentUser).build();

        given(groupAdapter.toModel(any(), any())).willReturn(sentModel);
        given(authService.getCurrentUser()).willReturn(currentUser);
        given(profileService.getByUsernameIfExistsAndUserEnabled(any())).willReturn(profile);
        given(groupRepository.save(any())).willReturn(saveModel);

        // when
        groupService.save(sentDto);

        verify(groupRepository, times(1)).save(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue().getId()).isNull();
        assertThat(argumentCaptor.getValue().getName()).isEqualTo("coucou subreddit");
        assertThat(argumentCaptor.getValue().getDescription()).isEqualTo("Elle est super bien ma description");
        assertThat(argumentCaptor.getValue().getImage()).isNull();
    }

    @Test
    void should_list_all_subreddits() {
        // given
        final int first = 1;
        final int size = 10;
        final List<Group> groups = Arrays.asList(
                Group.builder().id(1L).build(),
                Group.builder().id(2L).build(),
                Group.builder().id(3L).build(),
                Group.builder().id(4L).build(),
                Group.builder().id(5L).build(),
                Group.builder().id(10L).build(),
                Group.builder().id(12L).build(),
                Group.builder().id(13L).build(),
                Group.builder().id(14L).build(),
                Group.builder().id(15L).build(),
                Group.builder().id(16L).build(),
                Group.builder().id(17L).build()
        );
        final Pageable pageable = PageRequest.of(first, first * size);
        final Page<Group> subredditsPage = new PageImpl<>(groups.subList(0, size - 1));

        given(groupRepository.findAllByValidIsTrue(any(Pageable.class))).willReturn(subredditsPage);
        // when
        final Page<GroupDto> actuals = groupService.getAllValidGroups(pageable);

        // then
        assertThat(actuals.getTotalElements()).isEqualTo(subredditsPage.getTotalElements());
    }
}
