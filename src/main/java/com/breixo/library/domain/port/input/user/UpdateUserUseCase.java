package com.breixo.library.domain.port.input.user;

import com.breixo.library.domain.command.user.UpdateUserCommand;
import com.breixo.library.domain.model.user.User;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/** The Interface Update User Use Case. */
public interface UpdateUserUseCase {

    /**
     * Execute.
     *
     * @param updateUserCommand the update user command.
     * @return the updated user.
     */
    User execute(@Valid @NotNull UpdateUserCommand updateUserCommand);
}
