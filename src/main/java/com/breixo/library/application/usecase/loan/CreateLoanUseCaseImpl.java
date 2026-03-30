package com.breixo.library.application.usecase.loan;

import com.breixo.library.domain.command.loan.CreateLoanCommand;
import com.breixo.library.domain.event.LoanCreatedDomainEvent;
import com.breixo.library.domain.model.loan.Loan;
import com.breixo.library.domain.port.input.loan.CreateLoanUseCase;
import com.breixo.library.domain.port.output.loan.LoanCreationPersistencePort;
import com.breixo.library.domain.port.output.loan.LoanRetrievalPersistencePort;
import com.breixo.library.domain.port.output.book.BookRetrievalPersistencePort;
import com.breixo.library.domain.port.output.user.UserRetrievalPersistencePort;
import com.breixo.library.domain.service.LoanPolicyValidationService;
import com.breixo.library.domain.service.ReservationPolicyValidationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/** The Class Create Loan Use Case Impl. */
@Component
@RequiredArgsConstructor
public class CreateLoanUseCaseImpl implements CreateLoanUseCase {

    /** The user retrieval persistence port. */
    private final UserRetrievalPersistencePort userRetrievalPersistencePort;

    /** The book retrieval persistence port. */
    private final BookRetrievalPersistencePort bookRetrievalPersistencePort;

    /** The loan retrieval persistence port. */
    private final LoanRetrievalPersistencePort loanRetrievalPersistencePort;

    /** The loan policy validation service. */
    private final LoanPolicyValidationService loanPolicyValidationService;

    /** The reservation policy validation service. */
    private final ReservationPolicyValidationService reservationPolicyValidationService;

    /** The loan creation persistence port. */
    private final LoanCreationPersistencePort loanCreationPersistencePort;

    /** The application event publisher. */
    private final ApplicationEventPublisher applicationEventPublisher;

    /** {@inheritDoc} */
    @Override
    public Loan execute(@Valid @NotNull final CreateLoanCommand createLoanCommand) {

        final var user = this.userRetrievalPersistencePort.findById(createLoanCommand.userId());

        final var book = this.bookRetrievalPersistencePort.findById(createLoanCommand.bookId());

        final var loanList = this.loanRetrievalPersistencePort.findByUserId(user.id());

        this.loanPolicyValidationService.checkUserHasNoPendingFines(loanList);

        this.loanPolicyValidationService.checkCanBorrow(user, book, loanList);

        this.reservationPolicyValidationService.checkReservationPrecedence(user.id(), book.id());

        final var loan = this.loanCreationPersistencePort.execute(createLoanCommand);

        final var loanCreatedDomainEvent = LoanCreatedDomainEvent.builder()
                .userId(user.id())
                .bookId(book.id())
                .loanId(loan.id())
                .build();

        this.applicationEventPublisher.publishEvent(loanCreatedDomainEvent);

        return loan;
    }
}
