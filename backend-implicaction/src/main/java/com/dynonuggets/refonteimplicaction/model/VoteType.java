package com.dynonuggets.refonteimplicaction.model;


import com.dynonuggets.refonteimplicaction.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum VoteType {
    UPVOTE(1), DOWNVOTE(-1);

    private int direction;

    public static VoteType lookup(Integer direction) {
        return Arrays.stream(VoteType.values())
                .filter(value -> value.direction == direction)
                .findAny()
                .orElseThrow(() -> new NotFoundException("Vote not found"));
    }
}
