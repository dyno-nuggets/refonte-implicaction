package com.dynonuggets.refonteimplicaction.service;

import com.dynonuggets.refonteimplicaction.adapter.SubredditAdapter;
import com.dynonuggets.refonteimplicaction.dto.SubredditDto;
import com.dynonuggets.refonteimplicaction.model.FileModel;
import com.dynonuggets.refonteimplicaction.model.Subreddit;
import com.dynonuggets.refonteimplicaction.model.User;
import com.dynonuggets.refonteimplicaction.repository.FileRepository;
import com.dynonuggets.refonteimplicaction.repository.SubredditRepository;
import com.dynonuggets.refonteimplicaction.service.impl.S3CloudServiceImpl;
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
class SubredditServiceTest {

    @Mock
    SubredditRepository subredditRepository;

    @Mock
    SubredditAdapter subredditAdapter;

    @Mock
    AuthService authService;

    @Mock
    S3CloudServiceImpl cloudService;

    @Mock
    FileRepository fileRepository;

    @InjectMocks
    SubredditService subredditService;

    @Captor
    private ArgumentCaptor<Subreddit> argumentCaptor;

    @Test
    void should_save_subreddit_with_image() {
        // given
        final SubredditDto sentDto = SubredditDto.builder()
                .name("coucou subreddit")
                .description("Elle est super bien ma description")
                .build();

        final Subreddit sentModel = Subreddit.builder()
                .name("coucou subreddit")
                .description("Elle est super bien ma description")
                .build();

        final Subreddit saveModel = Subreddit.builder()
                .id(123L)
                .name("coucou subreddit")
                .description("Elle est super bien ma description")
                .build();

        final SubredditDto expectedDto = SubredditDto.builder()
                .id(123L)
                .name("coucou subreddit")
                .description("Elle est super bien ma description")
                .build();

        final User currentUser = User.builder().id(123L).build();

        final FileModel fileModel = FileModel.builder()
                .id(123L)
                .contentType("image/jpeg")
                .url("http://url.com")
                .filename("test.jpg")
                .build();

        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "user-file",
                "test.jpg",
                "image/jpeg",
                "test data".getBytes()
        );

        when(cloudService.uploadImage(any())).thenReturn(fileModel);
        when(fileRepository.save(fileModel)).thenReturn(fileModel);
        when(subredditAdapter.toModel(any())).thenReturn(sentModel);
        when(authService.getCurrentUser()).thenReturn(currentUser);
        when(subredditRepository.save(any())).thenReturn(saveModel);
        when(subredditAdapter.toDto(any())).thenReturn(expectedDto);

        // when
        subredditService.save(mockMultipartFile, sentDto);

        // then
        verify(subredditRepository, times(1)).save(argumentCaptor.capture());

        final Subreddit value = argumentCaptor.getValue();

        assertThat(value.getId()).isNull();
        assertThat(value.getName()).isEqualTo("coucou subreddit");
        assertThat(value.getDescription()).isEqualTo("Elle est super bien ma description");
        assertThat(value.getImage().getFilename()).isEqualTo("test.jpg");
    }


    @Test
    void should_save_when_no_image() {
        // given
        final SubredditDto sentDto = SubredditDto.builder()
                .name("coucou subreddit")
                .description("Elle est super bien ma description")
                .build();

        final Subreddit sentModel = Subreddit.builder()
                .name("coucou subreddit")
                .description("Elle est super bien ma description")
                .build();

        final User currentUser = User.builder().id(123L).build();

        final Subreddit saveModel = Subreddit.builder()
                .id(123L)
                .name("coucou subreddit")
                .description("Elle est super bien ma description")
                .build();

        given(subredditAdapter.toModel(any())).willReturn(sentModel);
        given(authService.getCurrentUser()).willReturn(currentUser);
        given(subredditRepository.save(any())).willReturn(saveModel);

        // when
        subredditService.save(sentDto);

        verify(subredditRepository, times(1)).save(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue().getId()).isNull();
        assertThat(argumentCaptor.getValue().getName()).isEqualTo("coucou subreddit");
        assertThat(argumentCaptor.getValue().getDescription()).isEqualTo("Elle est super bien ma description");
        assertThat(argumentCaptor.getValue().getImage()).isNull();
    }

    @Test
    void should_list_all_subreddits() {
        // given
        int first = 1;
        int size = 10;
        final List<Subreddit> subreddits = Arrays.asList(
                Subreddit.builder().id(1L).build(),
                Subreddit.builder().id(2L).build(),
                Subreddit.builder().id(3L).build(),
                Subreddit.builder().id(4L).build(),
                Subreddit.builder().id(5L).build(),
                Subreddit.builder().id(10L).build(),
                Subreddit.builder().id(12L).build(),
                Subreddit.builder().id(13L).build(),
                Subreddit.builder().id(14L).build(),
                Subreddit.builder().id(15L).build(),
                Subreddit.builder().id(16L).build(),
                Subreddit.builder().id(17L).build()
        );
        Pageable pageable = PageRequest.of(first, first * size);
        Page<Subreddit> subredditsPage = new PageImpl<>(subreddits.subList(0, size - 1));

        given(subredditRepository.findAll(any(Pageable.class))).willReturn(subredditsPage);

        // when
        Page<SubredditDto> actuals = subredditService.getAll(pageable);

        // then
        assertThat(actuals.getTotalElements()).isEqualTo(subredditsPage.getTotalElements());
    }
}
