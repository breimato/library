package com.breixo.library.domain.model.vo;

import java.util.Objects;
import java.util.Set;

import com.breixo.library.domain.exception.IsbnException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.BooleanUtils;

/** The Class Isbn. */
@Getter
@EqualsAndHashCode
public final class Isbn {

    /** The isbn length. */
    private static final int ISBN_LENGTH = 13;

    /** The check digit base length. */
    private static final int CHECK_DIGIT_BASE_LENGTH = 12;

    /** The odd weight. */
    private static final int ODD_WEIGHT = 1;

    /** The even weight. */
    private static final int EVEN_WEIGHT = 3;

    /** The ean modulus. */
    private static final int EAN_MODULUS = 10;

    /** The valid prefixes. */
    private static final Set<String> VALID_PREFIXES = Set.of("978", "979");

    /** The value. */
    private final String value;

    /**
     * Instantiates a new Isbn.
     *
     * @param rawValue the raw value.
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
     * @return the string.
     */
    private static String normalize(final String rawValue) {
        if (Objects.isNull(rawValue)) {
            throw new IsbnException(
                    ExceptionMessageConstants.INVALID_ISBN_CODE_ERROR,
                    ExceptionMessageConstants.INVALID_ISBN_FORMAT_MESSAGE_ERROR);
        }
        return rawValue.replace("-", "").replace(" ", "");
    }

    /**
     * Validate format.
     *
     * @param isbn the isbn.
     */
    private static void validateFormat(final String isbn) {
        if (BooleanUtils.isFalse(isValidLength(isbn)) || BooleanUtils.isFalse(isAllDigits(isbn))) {
            throw new IsbnException(
                    ExceptionMessageConstants.INVALID_ISBN_CODE_ERROR,
                    ExceptionMessageConstants.INVALID_ISBN_FORMAT_MESSAGE_ERROR);
        }
        if (VALID_PREFIXES.stream().noneMatch(isbn::startsWith)) {
            throw new IsbnException(
                    ExceptionMessageConstants.INVALID_ISBN_CODE_ERROR,
                    ExceptionMessageConstants.INVALID_ISBN_FORMAT_MESSAGE_ERROR);
        }
    }

    /**
     * Checks if valid length.
     *
     * @param isbn the isbn.
     * @return true, if is valid length.
     */
    private static boolean isValidLength(final String isbn) {
        return isbn.length() == ISBN_LENGTH;
    }

    /**
     * Checks if all digits.
     *
     * @param isbn the isbn.
     * @return true, if is all digits.
     */
    private static boolean isAllDigits(final String isbn) {
        return isbn.chars().allMatch(Character::isDigit);
    }

    /**
     * Validate check digit.
     *
     * @param isbn the isbn.
     */
    private static void validateCheckDigit(final String isbn) {
        var sum = 0;
        for (int i = 0; i < CHECK_DIGIT_BASE_LENGTH; i++) {
            final var digit = isbn.charAt(i) - '0';
            final var weight = i % 2 == 0 ? ODD_WEIGHT : EVEN_WEIGHT;
            sum += digit * weight;
        }

        final var expectedCheckDigit = (EAN_MODULUS - (sum % EAN_MODULUS)) % EAN_MODULUS;
        final var actualCheckDigit = isbn.charAt(ISBN_LENGTH - 1) - '0';

        if (expectedCheckDigit != actualCheckDigit) {
            throw new IsbnException(
                    ExceptionMessageConstants.INVALID_ISBN_CODE_ERROR,
                    ExceptionMessageConstants.INVALID_ISBN_CHECK_DIGIT_MESSAGE_ERROR);
        }
    }
}
