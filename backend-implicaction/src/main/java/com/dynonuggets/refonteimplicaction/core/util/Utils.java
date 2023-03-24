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

    /**
     * @param <T> le type de valeur
     * @return <code>defaultValue</code> si <code>nullableValue</code> est null sinon renvoi <code>nullableValue</code>
     */
    public static <T> T defaultIfNull(final T nullableValue, final T defaultValue) {
        return nullableValue != null ? nullableValue : defaultValue;
    }

    /**
     * @param nullableCollection une collection potentiellement nulle
     * @param <T>                type de la collection
     * @return un stream vide si <code>collection</code> est nul ou un stream débarrassé de toutes les valeurs nulles
     */
    public static <T> Stream<T> emptyStreamIfNull(final Collection<T> nullableCollection) {
        return emptyIfNull(nullableCollection).stream().filter(Objects::nonNull);
    }
}
