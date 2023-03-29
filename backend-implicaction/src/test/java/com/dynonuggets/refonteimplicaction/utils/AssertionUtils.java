package com.dynonuggets.refonteimplicaction.utils;

import com.dynonuggets.refonteimplicaction.core.error.BaseErrorResult;
import com.dynonuggets.refonteimplicaction.core.error.ImplicactionException;
import lombok.NoArgsConstructor;
import org.springframework.test.web.servlet.ResultActions;

import static java.lang.String.format;
import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@NoArgsConstructor(access = PRIVATE)
public class AssertionUtils {

    /**
     * Réalise les assertions de base sur les exceptions de type ImplicactionException
     *
     * @param actualException l'exception sur laquelle effectuer les assertions
     * @param classException  la classe de l’exception attendue
     * @param errorResult     l'objet ErrorResult de l’exception attendue
     * @param value           la valeur injectée dans le message de l’exception
     */
    public static void assertImplicactionException(final ImplicactionException actualException, final Class<?> classException, final BaseErrorResult errorResult, final String value) {
        assertThat(actualException)
                .isExactlyInstanceOf(classException)
                .extracting(ImplicactionException::getErrorResult)
                .isExactlyInstanceOf(errorResult.getClass())
                .isSameAs(errorResult);

        if (value != null) {
            assertThat(actualException.getMessage()).isEqualTo(format(errorResult.getMessage(), value));
        } else {
            assertThat(actualException.getMessage()).isEqualTo(errorResult.getMessage());
        }
    }

    /**
     * Réalise les assertions de base sur les exceptions de type ImplicactionException
     *
     * @param actualException l'exception sur laquelle effectuer les assertions
     * @param classException  la classe de l’exception attendue
     * @param errorResult     l'objet ErrorResult de l’exception attendue
     */
    public static void assertImplicactionException(final ImplicactionException actualException, final Class<?> classException, final BaseErrorResult errorResult) {
        assertImplicactionException(actualException, classException, errorResult, null);
    }

    public static void assertErrorResult(final ResultActions resultActions, final BaseErrorResult errorResult) throws Exception {
        resultActions
                .andExpect(status().is(errorResult.getStatus().value()))
                .andExpect(jsonPath("$.errorMessage", is(errorResult.getMessage())))
                .andExpect(jsonPath("$.errorCode", is(errorResult.getStatus().value())));
    }

    public static void assertErrorResultWithValue(final ResultActions resultActions, final BaseErrorResult errorResult, final String value) throws Exception {
        resultActions
                .andExpect(status().is(errorResult.getStatus().value()))
                .andExpect(jsonPath("$.errorMessage", is(String.format(errorResult.getMessage(), value))))
                .andExpect(jsonPath("$.errorCode", is(errorResult.getStatus().value())));
    }
}
