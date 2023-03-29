package com.dynonuggets.refonteimplicaction.core.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static com.dynonuggets.refonteimplicaction.core.utils.Utils.callIfNotNull;
import static com.dynonuggets.refonteimplicaction.core.utils.Utils.defaultIfNull;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

class UtilsTest {

    @Nested
    @DisplayName("# emptyStreamIfNull")
    class EmptyStreamIfNull {
        @Test
        @DisplayName("doit renvoyer un stream avec toutes les valeurs contenue dans la collection à l'appel de emptyStreamIfNull")
        void should_return_stream_containing_all_values_when_emptyStreamIfNull_and_all_values_are_non_null() {
            // given
            final List<String> collection = asList("Unos", "Dos", "Tres");

            // when
            final Stream<String> stringStream = Utils.emptyStreamIfNull(collection);

            // then
            assertThat(stringStream)
                    .isNotNull()
                    .doesNotContainNull()
                    .size()
                    .isEqualTo(collection.size());
        }

        @Test
        @DisplayName("doit retourner un stream vide à l'appel de emptyStreamIfNull avec une collection nulle")
        void should_return_empty_stream_when_emptyStreamIfNull_and_collection_is_null() {
            // when
            final Stream<String> stringStream = Utils.emptyStreamIfNull(null);

            // then
            assertThat(stringStream)
                    .isNotNull()
                    .isEmpty();
        }

        @Test
        @DisplayName("doit retourner un stream dont toutes les valeurs sont non nulles à l'appel de emptyStreamIfNull avec une collection contenant des valeurs nulles")
        void should_filter_null_values_when_emptyStreamIfNull_on_collection_containing_null_values() {
            // given
            final List<String> collection = asList("Unos", null, "Dos");

            // when
            final Stream<String> stringStream = Utils.emptyStreamIfNull(collection);

            // then
            assertThat(stringStream)
                    .isNotNull()
                    .doesNotContainNull()
                    .size()
                    .isEqualTo(collection.size() - collection.stream().filter(Objects::isNull).count());
        }
    }

    @Nested
    @DisplayName("# defaultIfNull")
    class DefaultIfNull {
        @Test
        @DisplayName("doit retourner defaultValue par défaut quand defaultIfNull est utilisée avec value à null")
        void should_return_default_value_when_defaultIfNull_and_value_is_null() {
            // given
            final String defaultValue = "defaultValue";

            // when
            final String result = defaultIfNull(null, defaultValue);

            // then
            assertThat(result).isEqualTo(defaultValue);
        }

        @Test
        @DisplayName("doit retourner value quand defaultIfNull est utilisée avec value non nulle")
        void should_return_value_when_defaultIfNull_and_value_is_not_null() {
            // given
            final String defaultValue = "defaultValue";
            final String value = "value";

            // when
            final String result = defaultIfNull(value, defaultValue);

            // then
            assertThat(result).isEqualTo(value);
        }
    }

    @Nested
    @DisplayName("# callIfNotNull")
    class CallIfNotNull {
        @Test
        @DisplayName("doit appeler le mapper quand callIfNotNull est appelée avec un input non null")
        void should_call_mapper_when_callIfNotNull_and_input_value_is_not_null() {
            // given
            final String input = "une chaine en minuscule";

            // when
            final String result = callIfNotNull(input, String::toUpperCase);

            // then
            assertThat(result)
                    .isEqualToIgnoringCase(input)
                    .isUpperCase();
        }

        @Test
        @DisplayName("doit retourner null quand callIfNotNull est appelée avec un input null")
        void should_return_null_when_callIfNotNull_and_input_is_null() {
            // when
            final String result = callIfNotNull((String) null, String::toUpperCase);

            // then
            assertThat(result).isNull();
        }
    }
}
