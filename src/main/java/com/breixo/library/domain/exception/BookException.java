package com.breixo.library.domain.exception;

import java.io.Serial;

import lombok.Getter;

/** The Class Book Exception. */
@Getter
public class BookException extends RuntimeException {

    /** The Constant serialVersionUID. */
    @Serial
    private static final long serialVersionUID = 1L;

    /** The code. */
    private final String code;

    /**
     * Instantiates a new book exception.
     *
     * @param code the code
     * @param message the message
     */
    public BookException(final String code, final String message) {
        super(message);
        this.code = code;
    }
}
