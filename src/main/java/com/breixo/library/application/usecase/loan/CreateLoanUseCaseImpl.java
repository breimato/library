package com.breixo.library.application.usecase.loan;

import com.breixo.library.domain.command.loan.CreateLoanCommand;
import com.breixo.library.domain.event.LoanCreatedDomainEvent;
import com.breixo.library.domain.exception.BookException;
import com.breixo.library.domain.exception.UserException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.book.Book;
import com.breixo.library.domain.model.loan.Loan;
import com.breixo.library.domain.model.user.User;
import com.breixo.library.domain.port.input.loan.CreateLoanUseCase;
import com.breixo.library.domain.port.output.book.BookRetrievalPersistencePort;
import com.breixo.library.domain.port.output.loan.LoanCreationPersistencePort;
import com.breixo.library.domain.port.output.loan.LoanRetrievalPersistencePort;
import com.breixo.library.domain.port.output.user.UserRetrievalPersistencePort;
import com.breixo.library.domain.port.input.book.BookPolicyValidationService;
import com.breixo.library.domain.port.input.loan.LoanPolicyValidationService;
import com.breixo.library.domain.port.input.reservation.ReservationPolicyValidationService;
import com.breixo.library.domain.port.input.user.UserPolicyValidationService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    /** The user policy validation service. */
    private final UserPolicyValidationService userPolicyValidationService;

    /** The book policy validation service. */
    private final BookPolicyValidationService bookPolicyValidationService;

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
    @Transactional
    public Loan execute(@Valid @NotNull final CreateLoanCommand createLoanCommand) {

        final var user = this.userRetrievalPersistencePort.findById(createLoanCommand.userId())
                .orElseThrow(() -> new UserException(
                        ExceptionMessageConstants.USER_NOT_FOUND_CODE_ERROR,
                        ExceptionMessageConstants.USER_NOT_FOUND_MESSAGE_ERROR));

        final var book = this.bookRetrievalPersistencePort.findById(createLoanCommand.bookId())
                .orElseThrow(() -> new BookException(
                        ExceptionMessageConstants.BOOK_NOT_FOUND_CODE_ERROR,
                        ExceptionMessageConstants.BOOK_NOT_FOUND_MESSAGE_ERROR));

        final var loanList = this.loanRetrievalPersistencePort.findByUserId(user.id());

        this.validate(user, book, loanList);

        final var loan = this.loanCreationPersistencePort.execute(createLoanCommand);

        final var loanCreatedDomainEvent = LoanCreatedDomainEvent.builder()
                .userId(user.id())
                .bookId(book.id())
                .loanId(loan.id())
                .build();

        this.applicationEventPublisher.publishEvent(loanCreatedDomainEvent);

        return loan;
    }

    /**
     * Validate.
     *
     * @param user     the user
     * @param book     the book
     * @param loanList the loan list
     */
    private void validate(final User user, final Book book, final List<Loan> loanList) {

        this.userPolicyValidationService.check(user, loanList);

        this.bookPolicyValidationService.checkIsBorrowable(book);

        this.loanPolicyValidationService.checkCanBorrow(book, loanList);

        this.reservationPolicyValidationService.checkPrecedence(user.id(), book.id());
    }
}
