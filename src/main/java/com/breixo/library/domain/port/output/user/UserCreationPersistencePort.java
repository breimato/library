package com.breixo.library.domain.port.output.user;

import com.breixo.library.domain.command.user.CreateUserCommand;
import com.breixo.library.domain.model.user.User;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/** The Interface User Creation Persistence Port. */
public interface UserCreationPersistencePort {

    /**
     * Execute.
     *
     * @param createUserCommand the create user command.
     * @return the created user.
     */
    User execute(@Valid @NotNull CreateUserCommand createUserCommand);
}
