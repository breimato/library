package com.breixo.library.domain.model.loan;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/** The Enum Loan Status. */
@Getter
@RequiredArgsConstructor
public enum LoanStatus {

    /** The Active. */
    ACTIVE(0),

    /** The Returned. */
    RETURNED(1),

    /** The Overdue. */
    OVERDUE(2);

    /** The id. */
    private final Integer id;
}
