package com.dynonuggets.refonteimplicaction.core.util;

import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.PRIVATE;
import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

@NoArgsConstructor(access = PRIVATE)
public class Utils {
    public static <I, O> O callIfNotNull(final I input, final Function<I, O> mapper) {
        return ofNullable(input)
                .map(mapper)
                .orElse(null);
    }

    public static <T> T defaultIfNull(final T nullableValue, final T defaultValue) {
        return nullableValue != null ? nullableValue : defaultValue;
    }

    public static <T> Stream<T> emptyStreamIfNull(final Collection<T> collection) {
        return emptyIfNull(collection).stream().filter(Objects::nonNull);
    }
}
