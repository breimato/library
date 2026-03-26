package com.breixo.library.domain.exception;

/** The Class Loan Exception. */
public class LoanException extends LibraryException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new loan exception.
     *
     * @param code    the code
     * @param message the message
     */
    public LoanException(final String code, final String message) {
        super(code, message);
    }
}
