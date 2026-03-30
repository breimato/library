package com.breixo.library.infrastructure.adapter.output.entities;

import java.time.LocalDateTime;

import lombok.Data;

/** The Class Reservation Entity. */
@Data
public class ReservationEntity {

    /** The id. */
    private Integer id;

    /** The user id. */
    private Integer userId;

    /** The book id. */
    private Integer bookId;

    /** The loan id. */
    private Integer loanId;

    /** The expires at. */
    private LocalDateTime expiresAt;

    /** The status id. */
    private Integer statusId;
}
