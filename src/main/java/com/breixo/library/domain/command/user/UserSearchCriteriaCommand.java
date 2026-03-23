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
    private final Long id;
}
