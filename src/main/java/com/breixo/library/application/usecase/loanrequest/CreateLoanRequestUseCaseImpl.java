package com.breixo.library.application.usecase.loanrequest;

import com.breixo.library.domain.command.loanrequest.CreateLoanRequestCommand;
import com.breixo.library.domain.model.loanrequest.LoanRequest;
import com.breixo.library.domain.model.user.enums.UserRole;
import com.breixo.library.domain.port.input.loanrequest.CreateLoanRequestUseCase;
import com.breixo.library.domain.port.input.user.AuthorizationService;
import com.breixo.library.domain.port.output.book.BookRetrievalPersistencePort;
import com.breixo.library.domain.port.output.loanrequest.LoanRequestCreationPersistencePort;
import com.breixo.library.domain.port.output.user.UserRetrievalPersistencePort;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/** The Class Create Loan Request Use Case Impl. */
@Component
@RequiredArgsConstructor
public class CreateLoanRequestUseCaseImpl implements CreateLoanRequestUseCase {

    /** The user retrieval persistence port. */
    private final UserRetrievalPersistencePort userRetrievalPersistencePort;

    /** The book retrieval persistence port. */
    private final BookRetrievalPersistencePort bookRetrievalPersistencePort;

    /** The loan request creation persistence port. */
    private final LoanRequestCreationPersistencePort loanRequestCreationPersistencePort;

    /** The authorization service. */
    private final AuthorizationService authorizationService;

    /** {@inheritDoc} */
    @Override
    @Transactional
    public LoanRequest execute(@Valid @NotNull final CreateLoanRequestCommand createLoanRequestCommand) {

        this.authorizationService.requireOwnResourceOrRole(
                createLoanRequestCommand.requesterId(),
                createLoanRequestCommand.userId(),
                UserRole.MANAGER);

        this.userRetrievalPersistencePort.findById(createLoanRequestCommand.userId());

        this.bookRetrievalPersistencePort.findById(createLoanRequestCommand.bookId());

        return this.loanRequestCreationPersistencePort.execute(createLoanRequestCommand);
    }
}
