package com.breixo.library.domain.model.fine.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/** The Enum Fine Status. */
@Getter
@RequiredArgsConstructor
public enum FineStatus {

    /** The Pending. */
    PENDING(0),

    /** The Paid. */
    PAID(1),

    /** The Waived. */
    WAIVED(2);

    /** The id. */
    private final Integer id;
}
