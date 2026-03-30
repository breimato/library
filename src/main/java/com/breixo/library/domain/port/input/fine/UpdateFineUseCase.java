package com.breixo.library.domain.port.input.fine;

import com.breixo.library.domain.command.fine.UpdateFineCommand;
import com.breixo.library.domain.model.fine.Fine;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/** The Interface Update Fine Use Case. */
public interface UpdateFineUseCase {

    /**
     * Execute.
     *
     * @param updateFineCommand the update fine command.
     * @return the fine.
     */
    Fine execute(@Valid @NotNull UpdateFineCommand updateFineCommand);
}
