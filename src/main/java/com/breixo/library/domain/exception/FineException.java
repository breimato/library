package com.breixo.library.domain.exception;

/** The Class Fine Exception. */
public class FineException extends LibraryException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new fine exception.
     *
     * @param code    the code
     * @param message the message
     */
    public FineException(final String code, final String message) {
        super(code, message);
    }
}
