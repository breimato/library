package com.breixo.library.domain.model.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/** The Enum User Role. */
@Getter
@RequiredArgsConstructor
public enum UserRole {

    /** The Normal. */
    NORMAL(0),

    /** The Manager. */
    MANAGER(1),

    /** The Admin. */
    ADMIN(2);

    /** The id. */
    private final Integer id;
}
