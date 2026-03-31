package com.breixo.library.application.usecase.reservation;

import com.breixo.library.domain.command.reservation.CreateReservationCommand;
import com.breixo.library.domain.model.book.Book;
import com.breixo.library.domain.model.loan.Loan;
import com.breixo.library.domain.model.reservation.Reservation;
import com.breixo.library.domain.model.user.User;
import com.breixo.library.domain.port.input.reservation.CreateReservationUseCase;
import com.breixo.library.domain.port.output.book.BookRetrievalPersistencePort;
import com.breixo.library.domain.port.output.loan.LoanRetrievalPersistencePort;
import com.breixo.library.domain.port.output.reservation.ReservationCreationPersistencePort;
import com.breixo.library.domain.port.output.user.UserRetrievalPersistencePort;
import com.breixo.library.domain.service.BookPolicyValidationService;
import com.breixo.library.domain.service.ReservationPolicyValidationService;
import com.breixo.library.domain.service.UserPolicyValidationService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/** The Class Create Reservation Use Case Impl. */
@Component
@RequiredArgsConstructor
public class CreateReservationUseCaseImpl implements CreateReservationUseCase {

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

    /** The reservation policy validation service. */
    private final ReservationPolicyValidationService reservationPolicyValidationService;

    /** The reservation creation persistence port. */
    private final ReservationCreationPersistencePort reservationCreationPersistencePort;

    /** {@inheritDoc} */
    @Override
    public Reservation execute(@Valid @NotNull final CreateReservationCommand createReservationCommand) {

        final var user = this.userRetrievalPersistencePort.findById(createReservationCommand.userId());

        final var book = this.bookRetrievalPersistencePort.findById(createReservationCommand.bookId());

        final var loanList = this.loanRetrievalPersistencePort.findByUserId(user.id());

        this.validate(user, book, loanList);

        return this.reservationCreationPersistencePort.execute(createReservationCommand);
    }

    private void validate(final User user, final Book book, final List<Loan> loanList) {

        this.userPolicyValidationService.check(user, loanList);

        this.bookPolicyValidationService.checkIsReservable(book);

        this.reservationPolicyValidationService.checkNoActiveReservation(user.id(), book.id());
    }
}
