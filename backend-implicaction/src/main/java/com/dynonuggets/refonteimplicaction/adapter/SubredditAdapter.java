package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.SubredditDto;
import com.dynonuggets.refonteimplicaction.model.Subreddit;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Component
@AllArgsConstructor
public class SubredditAdapter {

    public Subreddit toModel(final SubredditDto dto) {
        return Subreddit.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }

    public SubredditDto toDto(Subreddit model) {
        return SubredditDto.builder()
                .id(model.getId())
                .name(model.getName())
                .numberOfPosts(isNotEmpty(model.getPosts()) ? model.getPosts().size() : 0)
                .description(model.getDescription())
                .imageUrl(model.getImage() != null ? model.getImage().getUrl() : null)
                .build();
    }
}
