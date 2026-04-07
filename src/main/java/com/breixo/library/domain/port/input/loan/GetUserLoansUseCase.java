package com.breixo.library.domain.port.input.loan;

import java.util.List;

import com.breixo.library.domain.command.loan.GetUserLoansCommand;
import com.breixo.library.domain.model.loan.Loan;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/** The Interface Get User Loans Use Case. */
public interface GetUserLoansUseCase {

    /**
     * Execute.
     *
     * @param getUserLoansCommand the get user loans command.
     * @return the list of loans.
     */
    List<Loan> execute(@Valid @NotNull GetUserLoansCommand getUserLoansCommand);
}
