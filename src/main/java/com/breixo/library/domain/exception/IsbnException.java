package com.breixo.library.domain.exception;

import org.springframework.http.HttpStatus;

/** The Class Isbn Exception. */
public class IsbnException extends LibraryException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new Isbn Exception.
     *
     * @param code    the code.
     * @param message the message.
     */
    public IsbnException(final String code, final String message) {
        super(code, message, HttpStatus.BAD_REQUEST);
    }
}
