package com.breixo.library.domain.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

/** The Class Library Exception. */
@Getter
public abstract class LibraryException extends RuntimeException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The code. */
    private final String code;

    /** The http status. */
    private final HttpStatus httpStatus;

    /**
     * Instantiates a new library exception.
     *
     * @param code the code
     * @param message the message
     * @param httpStatus the http status
     */
    protected LibraryException(final String code, final String message, final HttpStatus httpStatus) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }
}
