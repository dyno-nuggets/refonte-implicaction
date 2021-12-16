package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.adapter.TrainingAdapter;
import com.dynonuggets.refonteimplicaction.adapter.UserAdapter;
import com.dynonuggets.refonteimplicaction.adapter.WorkExperienceAdapter;
import com.dynonuggets.refonteimplicaction.dto.UserDto;
import com.dynonuggets.refonteimplicaction.exception.UserNotFoundException;
import com.dynonuggets.refonteimplicaction.model.*;
import com.dynonuggets.refonteimplicaction.repository.FileRepository;
import com.dynonuggets.refonteimplicaction.repository.JobSeekerRepository;
import com.dynonuggets.refonteimplicaction.repository.RelationRepository;
import com.dynonuggets.refonteimplicaction.repository.UserRepository;
import com.dynonuggets.refonteimplicaction.service.impl.S3CloudServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    JobSeeker jobSeeker;
    UserDto expectedUserDto;
    User userSeeker;

    @Mock
    TrainingAdapter trainingAdapter;

    @Mock
    WorkExperienceAdapter experienceAdapter;

    @Mock
    UserRepository userRepository;

    @Mock
    RelationRepository relationRepository;

    @Mock
    AuthService authService;

    @Mock
    UserAdapter userAdapter;

    @Mock
    JobSeekerRepository jobSeekerRepository;

    @Mock
    S3CloudServiceImpl cloudService;

    @Mock
    FileRepository fileRepository;

    @InjectMocks
    UserService userService;

    @BeforeEach
    void setMockOutput() {

        List<Role> seekerRoles = Stream.of(RoleEnum.USER, RoleEnum.JOB_SEEKER)
                .map(roleEnum -> new Role(roleEnum.getId(), roleEnum.name(), Collections.emptySet()))
                .collect(toList());

        userSeeker = User.builder()
                .id(123L)
                .username("user")
                .password("password")
                .email("mail@mail.com")
                .firstname("prénom")
                .lastname("nom")
                .birthday(LocalDate.now())
                .url("http://google.com")
                .hobbies("hobbies")
                .purpose("purpose")
                .presentation("coucou")
                .expectation("rien")
                .contribution("tout")
                .phoneNumber("06666666")
                .registeredAt(Instant.now())
                .activatedAt(Instant.now())
                .activationKey("key")
                .active(true)
                .roles(seekerRoles)
                .build();

        List<WorkExperience> experiences = Arrays.asList(
                WorkExperience.builder().id(1L).startedAt(LocalDate.of(2002, 12, 10)).finishedAt(LocalDate.of(2003, 6, 12)).label("XP1").description("c'était super").companyName("compagnie 1").build(),
                WorkExperience.builder().id(2L).startedAt(LocalDate.of(2003, 12, 24)).finishedAt(LocalDate.of(2007, 6, 14)).label("XP2").description("c'était cool").companyName("compagnie 2").build()
        );

        List<Training> trainings = Arrays.asList(
                Training.builder().id(1L).label("Formation 1").date(LocalDate.of(2001, 10, 10)).school("School1").build(),
                Training.builder().id(2L).label("Formation 2").date(LocalDate.of(2002, 10, 10)).school("School1").build()
        );

        jobSeeker = JobSeeker.builder()
                .id(userSeeker.getId())
                .user(userSeeker)
                .experiences(experiences)
                .trainings(trainings)
                .build();

        expectedUserDto = UserDto.builder()
                .id(123L)
                .username("user")
                .email("mail@mail.com")
                .firstname("prénom")
                .lastname("nom")
                .birthday(LocalDate.now())
                .url("http://google.com")
                .hobbies("hobbies")
                .purpose("purpose")
                .presentation("coucou")
                .expectation("rien")
                .contribution("tout")
                .phoneNumber("06666666")
                .registeredAt(Instant.now())
                .activatedAt(Instant.now())
                .activationKey("key")
                .active(true)
                .experiences(experiences.stream().map(experienceAdapter::toDtoWithoutUser).collect(toList()))
                .trainings(trainings.stream().map(trainingAdapter::toDtoWithoutUser).collect(toList()))
                .roles(seekerRoles.stream().map(Role::getName).collect(toList()))
                .build();
    }

    @Test
    @DisplayName("Should Retrieve User by Id")
    void getUserByIdWithExistingUserId() {

        when(jobSeekerRepository.findById(userSeeker.getId())).thenReturn(Optional.of(jobSeeker));
        when(userAdapter.toDto(jobSeeker)).thenReturn(expectedUserDto);

        UserDto actualUser = userService.getUserById(123L);

        assertThat(actualUser.getId()).isEqualTo(expectedUserDto.getId());
    }

    @Test
    @DisplayName("Should Throw Exception")
    void getUserByIdWithNonExistingUserId() {
        final long notFoundId = 10000L;
        final String expectedMessage = "No user found with id " + notFoundId;

        when(jobSeekerRepository.findById(notFoundId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserById(notFoundId))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage(expectedMessage);
    }

    @Test
    void should_update_image_profile() {
        // given
        User currentUser = User.builder().id(123L).build();
        FileModel fileModel = FileModel.builder().objectKey("blabla").build();
        FileModel fileModel2 = FileModel.builder().id(4L).objectKey(fileModel.getObjectKey()).build();
        User currentUserAfterImageUpdate = User.builder().id(123L).image(fileModel2).build();
        UserDto userDto = UserDto.builder().id(123L).imageUrl("http://file.url/" + fileModel2.getObjectKey()).build();
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "user-file",
                "test.png",
                IMAGE_PNG_VALUE,
                "test data".getBytes()
        );
        given(authService.getCurrentUser()).willReturn(currentUser);
        given(cloudService.uploadImage(any())).willReturn(fileModel);
        given(fileRepository.save(any())).willReturn(fileModel2);
        given(userRepository.save(any())).willReturn(currentUserAfterImageUpdate);
        given(userAdapter.toDto(any(User.class))).willReturn(userDto);

        // when
        final UserDto actualUser = userService.updateImageProfile(mockMultipartFile);

        // then
        assertThat(actualUser.getImageUrl()).isEqualTo(userDto.getImageUrl());
    }
}
