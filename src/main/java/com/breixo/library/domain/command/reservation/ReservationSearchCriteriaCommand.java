package com.breixo.library.domain.command.reservation;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/** The Class Reservation Search Criteria Command. */
@Getter
@Builder
@EqualsAndHashCode
public class ReservationSearchCriteriaCommand {

    /** The id. */
    private final Integer id;

    /** The user id. */
    private final Integer userId;

    /** The book id. */
    private final Integer bookId;

    /** The loan id. */
    private final Integer loanId;

    /** The status id. */
    private final Integer statusId;
}
