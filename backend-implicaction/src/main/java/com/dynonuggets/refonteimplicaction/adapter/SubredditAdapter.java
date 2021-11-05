package com.dynonuggets.refonteimplicaction.adapter;

import com.dynonuggets.refonteimplicaction.dto.SubredditDto;
import com.dynonuggets.refonteimplicaction.model.Subreddit;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

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
                .description(model.getDescription())
                .build();
    }
}
