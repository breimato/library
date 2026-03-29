package com.breixo.library.domain.command.user;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * The Class User Search Criteria Command.
 */
@Getter
@Builder
@EqualsAndHashCode
public class UserSearchCriteriaCommand {

    /** The id. */
    private final Integer id;

    /** The name. */
    private final String name;

    /** The email. */
    private final String email;

    /** The status id. */
    private final Integer statusId;
}
