package com.breixo.library.domain.model.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/** The Enum User Status. */
@Getter
@RequiredArgsConstructor
public enum UserStatus {

    /** The Active. */
    ACTIVE(0),

    /** The Suspended. */
    SUSPENDED(1),

    /** The Blocked. */
    BLOCKED(2);

    /** The id. */
    private final Integer id;

}
