package com.breixo.library.domain.model.vo;

import com.breixo.library.domain.exception.IsbnException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;

import com.breixo.library.domain.vo.Isbn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** The Class Isbn Test. */
class IsbnTest {

    /** The Constant VALID_ISBN. */
    static final String VALID_ISBN = "9780134685991";

    /** The Constant VALID_ISBN_WITH_HYPHENS. */
    static final String VALID_ISBN_WITH_HYPHENS = "978-0-13-468599-1";

    /**
     * Test constructor when isbn is valid then create isbn.
     */
    @Test
    void testConstructor_whenIsbnIsValid_thenCreateIsbn() {
        // When
        final var isbn = new Isbn(VALID_ISBN);

        // Then
        assertEquals(VALID_ISBN, isbn.getValue());
    }

    /**
     * Test constructor when isbn has hyphens then strip and create isbn.
     */
    @Test
    void testConstructor_whenIsbnHasHyphens_thenStripAndCreateIsbn() {
        // When
        final var isbn = new Isbn(VALID_ISBN_WITH_HYPHENS);

        // Then
        assertEquals(VALID_ISBN, isbn.getValue());
    }

    /**
     * Test constructor when isbn is null then throw isbn exception.
     */
    @Test
    void testConstructor_whenIsbnIsNull_thenThrowIsbnException() {
        // When
        final var exception = assertThrows(IsbnException.class, () -> new Isbn(null));

        // Then
        assertEquals(ExceptionMessageConstants.INVALID_ISBN_FORMAT_MESSAGE_ERROR, exception.getMessage());
    }

    /**
     * Test constructor when isbn has wrong length then throw isbn exception.
     */
    @Test
    void testConstructor_whenIsbnHasWrongLength_thenThrowIsbnException() {
        // When
        final var exception = assertThrows(IsbnException.class, () -> new Isbn("978013468599"));

        // Then
        assertEquals(ExceptionMessageConstants.INVALID_ISBN_FORMAT_MESSAGE_ERROR, exception.getMessage());
    }

    /**
     * Test constructor when isbn contains non numeric characters then throw isbn exception.
     */
    @Test
    void testConstructor_whenIsbnContainsNonNumericCharacters_thenThrowIsbnException() {
        // When
        final var exception = assertThrows(IsbnException.class, () -> new Isbn("97801346859AB"));

        // Then
        assertEquals(ExceptionMessageConstants.INVALID_ISBN_FORMAT_MESSAGE_ERROR, exception.getMessage());
    }

    /**
     * Test constructor when isbn has invalid prefix then throw isbn exception.
     */
    @Test
    void testConstructor_whenIsbnHasInvalidPrefix_thenThrowIsbnException() {
        // When
        final var exception = assertThrows(IsbnException.class, () -> new Isbn("9770134685991"));

        // Then
        assertEquals(ExceptionMessageConstants.INVALID_ISBN_FORMAT_MESSAGE_ERROR, exception.getMessage());
    }

    /**
     * Test constructor when isbn has invalid check digit then throw isbn exception.
     */
    @Test
    void testConstructor_whenIsbnHasInvalidCheckDigit_thenThrowIsbnException() {
        // When
        final var exception = assertThrows(IsbnException.class, () -> new Isbn("9780134685990"));

        // Then
        assertEquals(ExceptionMessageConstants.INVALID_ISBN_CHECK_DIGIT_MESSAGE_ERROR, exception.getMessage());
    }

    /**
     * Test to string when isbn is valid then return value.
     */
    @Test
    void testToString_whenIsbnIsValid_thenReturnValue() {
        // Given
        final var isbn = new Isbn(VALID_ISBN);

        // When / Then
        assertEquals(VALID_ISBN, isbn.toString());
    }
}
