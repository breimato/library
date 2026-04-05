package com.breixo.library.domain.exception;

/** The Class Authorization Exception. */
public class AuthorizationException extends LibraryException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new authorization exception.
     *
     * @param code    the code
     * @param message the message
     */
    public AuthorizationException(final String code, final String message) {
        super(code, message);
    }
}
