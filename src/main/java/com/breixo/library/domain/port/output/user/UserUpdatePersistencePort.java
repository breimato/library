package com.breixo.library.domain.port.output.user;

import com.breixo.library.domain.command.user.UpdateUserCommand;
import com.breixo.library.domain.model.user.User;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/** The Interface User Update Persistence Port. */
public interface UserUpdatePersistencePort {

    /**
     * Execute.
     *
     * @param updateUserCommand the update user command.
     * @return the updated user.
     */
    User execute(@Valid @NotNull UpdateUserCommand updateUserCommand);
}
