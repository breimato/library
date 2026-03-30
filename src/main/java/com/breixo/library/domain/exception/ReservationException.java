package com.breixo.library.domain.exception;

/** The Class Reservation Exception. */
public class ReservationException extends LibraryException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new reservation exception.
     *
     * @param code    the code
     * @param message the message
     */
    public ReservationException(final String code, final String message) {
        super(code, message);
    }
}
