package com.breixo.library.domain.model.loanrequest.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/** The Enum Loan Request Status. */
@Getter
@RequiredArgsConstructor
public enum LoanRequestStatus {

    /** The Pending. */
    PENDING(0),

    /** The Approved. */
    APPROVED(1),

    /** The Rejected. */
    REJECTED(2),

    /** The Cancelled. */
    CANCELLED(3);

    /** The id. */
    private final Integer id;
}
