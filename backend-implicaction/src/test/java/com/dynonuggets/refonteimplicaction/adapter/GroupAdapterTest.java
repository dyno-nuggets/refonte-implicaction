package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.GroupDto;
import com.dynonuggets.refonteimplicaction.model.FileModel;
import com.dynonuggets.refonteimplicaction.model.Group;
import com.dynonuggets.refonteimplicaction.model.Post;
import com.dynonuggets.refonteimplicaction.model.User;
import com.dynonuggets.refonteimplicaction.repository.UserRepository;
import com.dynonuggets.refonteimplicaction.service.FileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GroupAdapterTest {

    @Mock
    FileService fileService;

    @InjectMocks
    GroupAdapter groupAdapter;

    @Mock
    UserRepository userRepository;

    @Test
    void should_map_to_model() {
        // given
        GroupDto expected = GroupDto.builder()
                .id(123L)
                .description("blablabla")
                .name("blabla")
                .userId(1L)
                .username("test")
                .build();

        User user = User.builder()
                .id(1L)
                .username("test")
                .build();

        // when
        final Group actual = groupAdapter.toModel(expected, user);

        // then
        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getDescription()).isEqualTo(expected.getDescription());
        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual.getImage()).isNull();
    }

    @Test
    void should_map_to_dto_with_count_when_model_has_posts() {
        // given
        User user = User.builder()
                .id(1L)
                .username("test")
                .build();

        Group expectedModel = Group.builder()
                .id(123L)
                .description("blablabla")
                .name("blabla")
                .posts(Arrays.asList(new Post(), new Post(), new Post(), new Post()))
                .createdAt(Instant.now())
                .user(user)
                .build();

        // when
        final GroupDto actualDto = groupAdapter.toDto(expectedModel);

        // then
        assertThat(actualDto).usingRecursiveComparison()
                .ignoringFields("user", "posts", "numberOfPosts", "imageUrl", "username", "userId", "numberOfUsers")
                .isEqualTo(expectedModel);

        assertThat(actualDto.getNumberOfPosts()).isEqualTo(expectedModel.getPosts().size());
        assertThat(actualDto.getImageUrl()).isEqualTo(GroupAdapter.DEFAULT_GROUP_IMAGE_URI);
        assertThat(actualDto.getUsername()).isEqualTo(expectedModel.getUser().getUsername());
        assertThat(actualDto.getUserId()).isEqualTo(expectedModel.getUser().getId());
    }

    @Test
    void should_map_to_dto_with_image_url_when_model_has_image() {
        // given
        User user = User.builder()
                .id(1L)
                .username("test")
                .build();

        Group expectedModel = Group.builder()
                .id(123L)
                .description("blablabla")
                .name("blabla")
                .image(FileModel.builder().url("http://url.com").objectKey("blablabla").build())
                .createdAt(Instant.now())
                .user(user)
                .build();

        String expectedUrl = "http://url/objectKey";

        given(fileService.buildFileUri(anyString())).willReturn(expectedUrl);

        // when
        final GroupDto actualDto = groupAdapter.toDto(expectedModel);

        // then
        assertThat(actualDto).usingRecursiveComparison()
                .ignoringFields("user", "posts", "numberOfPosts", "imageUrl", "username", "userId", "numberOfUsers")
                .isEqualTo(expectedModel);

        assertThat(actualDto.getNumberOfPosts()).isZero();
        assertThat(actualDto.getImageUrl()).contains(expectedUrl);
        assertThat(actualDto.getUsername()).isEqualTo(expectedModel.getUser().getUsername());
        assertThat(actualDto.getUserId()).isEqualTo(expectedModel.getUser().getId());
    }
}
