package com.breixo.library.domain.exception;

import org.springframework.http.HttpStatus;

/** The Class User Exception. */
public class UserException extends LibraryException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new user exception.
     *
     * @param code    the code
     * @param message the message
     */
    public UserException(final String code, final String message) {
        super(code, message, HttpStatus.NOT_FOUND);
    }
}
