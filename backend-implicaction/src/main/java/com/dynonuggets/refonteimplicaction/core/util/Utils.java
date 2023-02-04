package com.dynonuggets.refonteimplicaction.core.util;

import lombok.NoArgsConstructor;

import java.util.function.Function;

import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class Utils {
    public static <I, O> O callIfNotNull(final I input, final Function<I, O> mapper) {
        return ofNullable(input)
                .map(mapper)
                .orElse(null);
    }
}
