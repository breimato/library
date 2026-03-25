package com.breixo.library.domain.exception;

/** The Class Book Exception. */
public class BookException extends LibraryException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new book exception.
     *
     * @param code    the code
     * @param message the message
     */
    public BookException(final String code, final String message) {
        super(code, message);
    }
}
