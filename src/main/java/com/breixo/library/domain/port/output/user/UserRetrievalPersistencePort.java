package com.breixo.library.domain.port.output.user;

import java.util.List;

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
     * @return the list of users.
     */
    List<User> find(@Valid @NotNull UserSearchCriteriaCommand userSearchCriteriaCommand);
}
