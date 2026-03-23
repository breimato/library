package com.breixo.library.domain.model.vo;

import com.breixo.library.domain.exception.IsbnException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * The Class Isbn.
 *
 * <p>Value Object representing a valid ISBN-13. Validates:
 * <ul>
 *   <li>Exactly 13 digits (hyphens and spaces are stripped before validation)</li>
 *   <li>Prefix must be 978 or 979</li>
 *   <li>EAN-13 check digit (alternating weights 1 and 3)</li>
 * </ul>
 */
@Getter
@EqualsAndHashCode
public final class Isbn {

    /** The value. */
    private final String value;

    /**
     * Instantiates a new Isbn.
     *
     * @param rawValue the raw isbn value (hyphens and spaces are stripped automatically).
     */
    public Isbn(final String rawValue) {
        final var normalized = normalize(rawValue);
        validateFormat(normalized);
        validateCheckDigit(normalized);
        this.value = normalized;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return this.value;
    }

    /**
     * Normalize.
     *
     * @param rawValue the raw value.
     * @return the normalized isbn string.
     */
    private static String normalize(final String rawValue) {
        if (rawValue == null) {
            return null;
        }
        return rawValue.replace("-", "").replace(" ", "");
    }

    /**
     * Validate format.
     *
     * @param isbn the normalized isbn.
     */
    private static void validateFormat(final String isbn) {
        if (isbn == null || isbn.length() != 13 || !isbn.matches("\\d{13}")) {
            throw new IsbnException(
                    ExceptionMessageConstants.INVALID_ISBN_CODE_ERROR,
                    ExceptionMessageConstants.INVALID_ISBN_FORMAT_MESSAGE_ERROR);
        }
        if (!isbn.startsWith("978") && !isbn.startsWith("979")) {
            throw new IsbnException(
                    ExceptionMessageConstants.INVALID_ISBN_CODE_ERROR,
                    ExceptionMessageConstants.INVALID_ISBN_FORMAT_MESSAGE_ERROR);
        }
    }

    /**
     * Validate check digit.
     *
     * @param isbn the normalized isbn.
     */
    private static void validateCheckDigit(final String isbn) {
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            final int digit = isbn.charAt(i) - '0';
            sum += (i % 2 == 0) ? digit : digit * 3;
        }
        final int expectedCheckDigit = (10 - (sum % 10)) % 10;
        final int actualCheckDigit = isbn.charAt(12) - '0';
        if (expectedCheckDigit != actualCheckDigit) {
            throw new IsbnException(
                    ExceptionMessageConstants.INVALID_ISBN_CODE_ERROR,
                    ExceptionMessageConstants.INVALID_ISBN_CHECK_DIGIT_MESSAGE_ERROR);
        }
    }
}
