package com.breixo.library.domain.exception;

/** The Class Forbidden Action Exception. */
public class ForbiddenActionException extends LibraryException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new forbidden action exception.
     *
     * @param code    the code
     * @param message the message
     */
    public ForbiddenActionException(final String code, final String message) {
        super(code, message);
    }
}
