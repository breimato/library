package com.breixo.library.domain.port.input.user;

import com.breixo.library.domain.command.user.CreateUserCommand;
import com.breixo.library.domain.model.user.User;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/** The Interface Create User Use Case. */
public interface CreateUserUseCase {

    /**
     * Execute.
     *
     * @param createUserCommand the create user command.
     * @return the created user.
     */
    User execute(@Valid @NotNull CreateUserCommand createUserCommand);
}
