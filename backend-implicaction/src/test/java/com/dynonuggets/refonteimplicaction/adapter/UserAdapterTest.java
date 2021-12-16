package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.UserDto;
import com.dynonuggets.refonteimplicaction.model.*;
import com.dynonuggets.refonteimplicaction.service.FileService;
import com.dynonuggets.refonteimplicaction.utils.ApiUrls;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserAdapterTest {

    User userRecruiter;
    Recruiter recruiter;
    List<Role> recruiterRoles;

    JobSeeker jobSeeker;
    Company company;

    User userSeeker;
    List<WorkExperience> experiences;
    List<Training> trainings;
    List<Role> seekerRoles;

    @InjectMocks
    UserAdapter userAdapter;

    @Mock
    WorkExperienceAdapter workExperienceAdapter;

    @Mock
    TrainingAdapter trainingAdapter;

    @Mock
    CompanyAdapter companyAdapter;

    @Mock
    FileService fileService;

    @Value("${app.url}")
    String appUrl;

    private void initRecruiter() {
        recruiterRoles = Stream.of(RoleEnum.USER, RoleEnum.RECRUITER)
                .map(roleEnum -> new Role(roleEnum.getId(), roleEnum.name(), Collections.emptySet()))
                .collect(toList());

        company = Company.builder()
                .id(321L)
                .name("compagnie")
                .logo("logo")
                .description("description")
                .url("https://google.com")
                .build();

        userRecruiter = User.builder()
                .id(123L)
                .username("user")
                .password("password")
                .email("mail@mail.com")
                .firstname("prénom")
                .lastname("nom")
                .birthday(LocalDate.now())
                .url("https://google.com")
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
                .roles(recruiterRoles)
                .build();

        recruiter = Recruiter.builder()
                .id(userRecruiter.getId())
                .user(userRecruiter)
                .company(company)
                .build();
    }

    private void initSeeker() {
        experiences = Arrays.asList(
                WorkExperience.builder().id(1L).startedAt(LocalDate.of(2002, 12, 10)).finishedAt(LocalDate.of(2003, 6, 12)).label("XP1").description("c'était super").companyName("compagnie 1").build(),
                WorkExperience.builder().id(2L).startedAt(LocalDate.of(2003, 12, 24)).finishedAt(LocalDate.of(2007, 6, 14)).label("XP2").description("c'était cool").companyName("compagnie 2").build()
        );

        trainings = Arrays.asList(
                Training.builder().id(1L).label("Formation 1").date(LocalDate.of(2001, 10, 10)).school("School1").build(),
                Training.builder().id(2L).label("Formation 2").date(LocalDate.of(2002, 10, 10)).school("School1").build()
        );

        seekerRoles = Stream.of(RoleEnum.USER, RoleEnum.JOB_SEEKER)
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

        jobSeeker = JobSeeker.builder()
                .user(userRecruiter)
                .experiences(experiences)
                .trainings(trainings)
                .build();
    }

    @BeforeEach
    public void setUp() {
        initRecruiter();
        initSeeker();
    }

    @Test
    void toDtoTestWillNullImage() {
        final UserDto userDto = userAdapter.toDto(userRecruiter);

        assertThat(userDto.getId()).isEqualTo(userRecruiter.getId());
        assertThat(userDto.getUsername()).isEqualTo(userRecruiter.getUsername());
        assertThat(userDto.getEmail()).isEqualTo(userRecruiter.getEmail());
        assertThat(userDto.getUrl()).isEqualTo(userRecruiter.getUrl());
        assertThat(userDto.getRegisteredAt()).isEqualTo(userRecruiter.getRegisteredAt());
        assertThat(userDto.getRegisteredAt()).isEqualTo(userRecruiter.getRegisteredAt());
        assertThat(userDto.getBirthday()).isEqualTo(userRecruiter.getBirthday());
        assertThat(userDto.getImageUrl()).isEqualTo(UserAdapter.DEFAULT_USER_IMAGE_URI);
    }

    @Test
    void toDtoTestWithImage() {
        // given
        FileModel image = FileModel.builder().objectKey("azertyKey").build();
        User user = User.builder().image(image).build();
        final UserDto expectedDto = UserDto.builder()
                .roles(emptyList())
                .imageUrl(appUrl + ApiUrls.FILE_BASE_URI + ApiUrls.GET_FILE_BY_KEY.replace("{objectKey}", image.getObjectKey()))
                .build();
        given(fileService.buildFileUri(anyString())).willReturn(appUrl + ApiUrls.FILE_BASE_URI + ApiUrls.GET_FILE_BY_KEY.replace("{objectKey}", image.getObjectKey()));

        // when
        final UserDto userDto = userAdapter.toDto(user);

        // then
        assertThat(userDto).usingRecursiveComparison().isEqualTo(expectedDto);
    }

    @Test
    void toDtoJobSeekerTest() {
        final UserDto userDto = userAdapter.toDto(jobSeeker);
        assertThat(userDto.getId()).isEqualTo(userRecruiter.getId());
        assertThat(userDto.getUsername()).isEqualTo(userRecruiter.getUsername());
        assertThat(userDto.getEmail()).isEqualTo(userRecruiter.getEmail());
        assertThat(userDto.getUrl()).isEqualTo(userRecruiter.getUrl());
        assertThat(userDto.getRegisteredAt()).isEqualTo(userRecruiter.getRegisteredAt());
        assertThat(userDto.getRegisteredAt()).isEqualTo(userRecruiter.getRegisteredAt());
        assertThat(userDto.getBirthday()).isEqualTo(userRecruiter.getBirthday());
    }

    @Test
    void toDtoJobSeekerTestWithEmptyTrainingTest() {
        final JobSeeker jobSeeker = JobSeeker.builder()
                .user(userSeeker)
                .trainings(null)
                .build();

        final UserDto actual = userAdapter.toDto(jobSeeker);

        assertThat(actual.getTrainings().isEmpty()).isTrue();
    }

    @Test
    void toDtoJobSeekerTestWithEmptyExperienceTest() {
        final JobSeeker jobSeeker = JobSeeker.builder()
                .user(userSeeker)
                .experiences(null)
                .build();

        final UserDto actual = userAdapter.toDto(jobSeeker);

        assertThat(actual.getExperiences().isEmpty()).isTrue();
    }

    @Test
    void toDtoRecruiterTest() {
        final UserDto expectedDto = UserDto.builder()
                .id(userRecruiter.getId())
                .username(userRecruiter.getUsername())
                .firstname(userRecruiter.getFirstname())
                .lastname(userRecruiter.getLastname())
                .email(userRecruiter.getEmail())
                .url(userRecruiter.getUrl())
                .hobbies(userRecruiter.getHobbies())
                .purpose(userRecruiter.getPurpose())
                .registeredAt(userRecruiter.getRegisteredAt())
                .presentation(userRecruiter.getPresentation())
                .contribution(userRecruiter.getContribution())
                .birthday(userRecruiter.getBirthday())
                .phoneNumber(userRecruiter.getPhoneNumber())
                .activationKey(userRecruiter.getActivationKey())
                .activatedAt(userRecruiter.getActivatedAt())
                .expectation(userRecruiter.getExpectation())
                .active(userRecruiter.isActive())
                .roles(userRecruiter.getRoles().stream().map(Role::getName).collect(toList()))
                .company(companyAdapter.toDto(company))
                .imageUrl(UserAdapter.DEFAULT_USER_IMAGE_URI)
                .build();

        final UserDto actualDto = userAdapter.toDto(recruiter);

        assertThat(actualDto).usingRecursiveComparison()
                .isEqualTo(expectedDto);
    }

    @Test
    void toDtoLightWithNullImageTest() {
        UserDto expectedDto = UserDto.builder()
                .id(userSeeker.getId())
                .username(userSeeker.getUsername())
                .roles(userSeeker.getRoles().stream().map(Role::getName).collect(toList()))
                .build();
        UserDto actualDto = userAdapter.toDtoLight(userSeeker);

        assertThat(actualDto).usingRecursiveComparison()
                .isEqualTo(expectedDto);
    }

    @Test
    void toDtoLightWithImageTest() {
        // given
        FileModel image = FileModel.builder().objectKey("azertyKey").build();
        User user = User.builder()
                .image(image)
                .build();
        UserDto expectedDto = UserDto.builder()
                .roles(emptyList())
                .imageUrl(appUrl + ApiUrls.FILE_BASE_URI + ApiUrls.GET_FILE_BY_KEY.replace("{objectKey}", image.getObjectKey()))
                .build();

        given(fileService.buildFileUri(anyString())).willReturn(appUrl + ApiUrls.FILE_BASE_URI + ApiUrls.GET_FILE_BY_KEY.replace("{objectKey}", image.getObjectKey()));

        // when
        UserDto actualDto = userAdapter.toDtoLight(user);

        // then
        assertThat(actualDto).usingRecursiveComparison()
                .isEqualTo(expectedDto);
    }

    @Test
    void toModelTest() {
        final UserDto dto = userAdapter.toDto(userRecruiter);

        final User actualUser = userAdapter.toModel(dto);

        assertThat(actualUser).usingRecursiveComparison()
                .ignoringFields("password")
                .isEqualTo(userRecruiter);
    }
}
