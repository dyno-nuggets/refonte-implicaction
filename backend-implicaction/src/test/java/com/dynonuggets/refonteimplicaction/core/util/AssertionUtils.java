package com.dynonuggets.refonteimplicaction.core.util;

import com.dynonuggets.refonteimplicaction.core.error.BaseErrorResult;
import com.dynonuggets.refonteimplicaction.core.error.ImplicactionException;
import lombok.NoArgsConstructor;

import static java.lang.String.format;
import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;

@NoArgsConstructor(access = PRIVATE)
public class AssertionUtils {
    /**
     * Réalise les assertions de base sur les exceptions de type ImplicactionException
     *
     * @param actualException l'exception sur laquelle effectuer les assertions
     * @param classException  la classe de l'exception attendue
     * @param errorResult     l'objet ErrorResult de l'exception attendue
     * @param value           la valeur injectée dans le message de l'exception
     */
    public static void assertImplicactionException(final ImplicactionException actualException, final Class<?> classException, final BaseErrorResult errorResult, final String value) {
        assertThat(actualException)
                .isExactlyInstanceOf(classException)
                .extracting(ImplicactionException::getErrorResult)
                .isExactlyInstanceOf(errorResult.getClass())
                .isSameAs(errorResult);

        assertThat(actualException.getMessage())
                .isEqualTo(format(errorResult.getMessage(), value));
    }
}