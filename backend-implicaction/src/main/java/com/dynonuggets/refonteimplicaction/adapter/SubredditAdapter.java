package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.SubredditDto;
import com.dynonuggets.refonteimplicaction.model.Subreddit;
import com.dynonuggets.refonteimplicaction.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Component
@AllArgsConstructor
public class SubredditAdapter {

    protected static final String DEFAULT_GROUP_IMAGE_URI = "assets/img/avatar-ia-group.png";

    private FileService fileService;

    public Subreddit toModel(final SubredditDto dto) {
        return Subreddit.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }

    public SubredditDto toDto(Subreddit model) {
        final String imageUrl = model.getImage() != null ? fileService.buildFileUri(model.getImage().getObjectKey()) : DEFAULT_GROUP_IMAGE_URI;
        return SubredditDto.builder()
                .id(model.getId())
                .name(model.getName())
                .numberOfPosts(isNotEmpty(model.getPosts()) ? model.getPosts().size() : 0)
                .description(model.getDescription())
                .imageUrl(imageUrl)
                .build();
    }
}
