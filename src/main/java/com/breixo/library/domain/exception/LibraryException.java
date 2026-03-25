package com.breixo.library.domain.exception;

import lombok.Getter;

/** The Class Library Exception. */
@Getter
public abstract class LibraryException extends RuntimeException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The code. */
    private final String code;

    /**
     * Instantiates a new library exception.
     *
     * @param code    the code
     * @param message the message
     */
    protected LibraryException(final String code, final String message) {
        super(message);
        this.code = code;
    }
}
