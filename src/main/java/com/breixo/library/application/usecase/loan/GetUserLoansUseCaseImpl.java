package com.breixo.library.application.usecase.loan;

import java.util.List;

import com.breixo.library.domain.command.loan.GetUserLoansCommand;
import com.breixo.library.domain.model.loan.Loan;
import com.breixo.library.domain.model.user.enums.UserRole;
import com.breixo.library.domain.port.input.loan.GetUserLoansUseCase;
import com.breixo.library.domain.port.input.user.AuthorizationService;
import com.breixo.library.domain.port.output.loan.LoanRetrievalPersistencePort;
import com.breixo.library.domain.port.output.user.UserRetrievalPersistencePort;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** The Class Get User Loans Use Case Impl. */
@Component
@RequiredArgsConstructor
public class GetUserLoansUseCaseImpl implements GetUserLoansUseCase {

    /** The authorization service. */
    private final AuthorizationService authorizationService;

    /** The user retrieval persistence port. */
    private final UserRetrievalPersistencePort userRetrievalPersistencePort;

    /** The loan retrieval persistence port. */
    private final LoanRetrievalPersistencePort loanRetrievalPersistencePort;

    /** {@inheritDoc} */
    @Override
    public List<Loan> execute(@Valid @NotNull final GetUserLoansCommand getUserLoansCommand) {

        this.authorizationService.requireOwnResourceOrRole(
                getUserLoansCommand.requesterId(),
                getUserLoansCommand.userId(),
                UserRole.MANAGER);

        this.userRetrievalPersistencePort.findById(getUserLoansCommand.userId());

        return this.loanRetrievalPersistencePort.findByUserId(getUserLoansCommand.userId());
    }
}
