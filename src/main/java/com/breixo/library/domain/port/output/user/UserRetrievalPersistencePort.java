package com.breixo.library.domain.port.output.user;

import java.util.Optional;

import com.breixo.library.domain.command.user.UserSearchCriteriaCommand;
import com.breixo.library.domain.model.user.User;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/** The Interface User Retrieval Persistence Port. */
public interface UserRetrievalPersistencePort {

    /**
     * Execute.
     *
     * @param userSearchCriteriaCommand the user search criteria command.
     * @return the user, or empty if not found.
     */
    Optional<User> execute(@Valid @NotNull UserSearchCriteriaCommand userSearchCriteriaCommand);
}
