package com.breixo.library.domain.exception;

/** The Class Loan Request Exception. */
public class LoanRequestException extends LibraryException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new loan request exception.
     *
     * @param code    the code
     * @param message the message
     */
    public LoanRequestException(final String code, final String message) {
        super(code, message);
    }
}
