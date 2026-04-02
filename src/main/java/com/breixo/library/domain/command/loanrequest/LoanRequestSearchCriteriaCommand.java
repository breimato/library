package com.breixo.library.domain.command.loanrequest;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * The Class Loan Request Search Criteria Command.
 */
@Getter
@Builder
@EqualsAndHashCode
public class LoanRequestSearchCriteriaCommand {

    /** The id. */
    private final Integer id;

    /** The user id. */
    private final Integer userId;

    /** The book id. */
    private final Integer bookId;

    /** The status id. */
    private final Integer statusId;
}
