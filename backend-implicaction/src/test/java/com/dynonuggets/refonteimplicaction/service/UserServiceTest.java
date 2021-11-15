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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    TrainingAdapter trainingAdapter;

    @Mock
    WorkExperienceAdapter experienceAdapter;

    private JobSeeker jobSeeker;
    private UserDto expectedUserDto;
    private User userSeeker;
    private List<Role> seekerRoles;
    private List<WorkExperience> experiences;
    private List<Training> trainings;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RelationRepository relationRepository;

    @Mock
    private AuthService authService;

    @Mock
    private UserAdapter userAdapter;

    @Mock
    private JobSeekerRepository jobSeekerRepository;

    @Mock
    private S3CloudServiceImpl cloudService;

    @Mock
    private FileRepository fileRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setMockOutput() {

        seekerRoles = Stream.of(RoleEnum.USER, RoleEnum.JOB_SEEKER)
                .map(roleEnum -> new Role(roleEnum.getId(), roleEnum.getName(), Collections.emptySet()))
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

        experiences = Arrays.asList(
                WorkExperience.builder().id(1L).startedAt(LocalDate.of(2002, 12, 10)).finishedAt(LocalDate.of(2003, 6, 12)).label("XP1").description("c'était super").companyName("compagnie 1").build(),
                WorkExperience.builder().id(2L).startedAt(LocalDate.of(2003, 12, 24)).finishedAt(LocalDate.of(2007, 6, 14)).label("XP2").description("c'était cool").companyName("compagnie 2").build()
        );

        trainings = Arrays.asList(
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
}
