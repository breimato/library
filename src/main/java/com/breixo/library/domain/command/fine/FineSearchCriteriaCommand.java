package com.breixo.library.domain.command.fine;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/** The Class Fine Search Criteria Command. */
@Getter
@Builder
@EqualsAndHashCode
public class FineSearchCriteriaCommand {

    /** The id. */
    private final Integer id;

    /** The loan id. */
    private final Integer loanId;

    /** The status id. */
    private final Integer statusId;

    /** The user id (filters fines belonging to this user's loans). */
    private final Integer userId;
}
