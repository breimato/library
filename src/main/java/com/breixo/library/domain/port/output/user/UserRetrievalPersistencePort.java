package com.breixo.library.domain.port.output.user;

import java.util.List;
import java.util.Optional;

import com.breixo.library.domain.command.user.UserSearchCriteriaCommand;
import com.breixo.library.domain.model.user.User;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/** The Interface User Retrieval Persistence Port. */
public interface UserRetrievalPersistencePort {

    /**
     * Find.
     *
     * @param userSearchCriteriaCommand the user search criteria command.
     * @return the user, or empty if not found.
     */
    Optional<User> find(@Valid @NotNull UserSearchCriteriaCommand userSearchCriteriaCommand);

    /**
     * Find all.
     *
     * @param userSearchCriteriaCommand the user search criteria command.
     * @return the list of users.
     */
    List<User> findAll(@Valid @NotNull UserSearchCriteriaCommand userSearchCriteriaCommand);
}
