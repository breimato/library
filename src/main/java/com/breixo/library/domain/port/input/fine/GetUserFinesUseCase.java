package com.breixo.library.domain.port.input.fine;

import java.util.List;

import com.breixo.library.domain.command.fine.GetUserFinesCommand;
import com.breixo.library.domain.model.fine.Fine;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/** The Interface Get User Fines Use Case. */
public interface GetUserFinesUseCase {

    /**
     * Execute.
     *
     * @param getUserFinesCommand the get user fines command.
     * @return the list of fines.
     */
    List<Fine> execute(@Valid @NotNull GetUserFinesCommand getUserFinesCommand);
}
