package com.breixo.library.application.usecase.loan;

import java.util.List;

import com.breixo.library.domain.command.book.BookSearchCriteriaCommand;
import com.breixo.library.domain.command.loan.CreateLoanCommand;
import com.breixo.library.domain.command.loan.LoanSearchCriteriaCommand;
import com.breixo.library.domain.command.fine.FineSearchCriteriaCommand;
import com.breixo.library.domain.command.user.UserSearchCriteriaCommand;
import com.breixo.library.domain.exception.BookException;
import com.breixo.library.domain.exception.UserException;
import com.breixo.library.domain.exception.LoanException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.book.Book;
import com.breixo.library.domain.model.fine.enums.FineStatus;
import com.breixo.library.domain.model.loan.Loan;
import com.breixo.library.domain.model.user.User;
import com.breixo.library.domain.port.input.loan.CreateLoanUseCase;
import com.breixo.library.domain.port.output.book.BookRetrievalPersistencePort;
import com.breixo.library.domain.port.output.fine.FineRetrievalPersistencePort;
import com.breixo.library.domain.port.output.loan.LoanCreationPersistencePort;
import com.breixo.library.domain.port.output.loan.LoanRetrievalPersistencePort;
import com.breixo.library.domain.port.output.user.UserRetrievalPersistencePort;
import com.breixo.library.domain.service.LoanPolicyValidationService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
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

    /** The fine retrieval persistence port. */
    private final FineRetrievalPersistencePort fineRetrievalPersistencePort;

    /** The loan creation persistence port. */
    private final LoanCreationPersistencePort loanCreationPersistencePort;

    /** {@inheritDoc} */
    @Override
    public Loan execute(@Valid @NotNull final CreateLoanCommand createLoanCommand) {

        final var user = this.validateUser(createLoanCommand.userId());

        final var book = this.validateBook(createLoanCommand.bookId());

        final var loanSearchCriteriaCommand = LoanSearchCriteriaCommand.builder()
                .userId(user.id())
                .build();
        final var loanList = this.loanRetrievalPersistencePort.find(loanSearchCriteriaCommand);

        this.loanPolicyValidationService.checkCanBorrow(user, book, loanList);
        this.validateUserHasNoPendingFines(loanList);

        return this.loanCreationPersistencePort.execute(createLoanCommand);
    }


    /**
     * Validate user has no pending fines.
     *
     * @param loanList the loan list
     */
    private void validateUserHasNoPendingFines(final List<Loan> loanList) {

        final var hasPendingFines = loanList.stream()
                .anyMatch(loan -> {

                    final var fineSearchCriteriaCommand = FineSearchCriteriaCommand.builder()
                            .loanId(loan.id())
                            .statusId(FineStatus.PENDING.getId())
                            .build();

                    final var fineList = this.fineRetrievalPersistencePort.find(fineSearchCriteriaCommand);

                    return CollectionUtils.isNotEmpty(fineList);
                });

        if (hasPendingFines) {
            throw new LoanException(
                    ExceptionMessageConstants.USER_HAS_PENDING_FINES_CODE_ERROR,
                    ExceptionMessageConstants.USER_HAS_PENDING_FINES_MESSAGE_ERROR);
        }
    }


    /**
     * Validate user.
     *
     * @param userId the user id
     * @return the user
     */
    private User validateUser(final Integer userId) {

        final var userSearchCriteriaCommand = UserSearchCriteriaCommand.builder().id(userId).build();
        final var users = this.userRetrievalPersistencePort.find(userSearchCriteriaCommand);

        if (CollectionUtils.isEmpty(users)) {
            throw new UserException(
                    ExceptionMessageConstants.USER_NOT_FOUND_CODE_ERROR,
                    ExceptionMessageConstants.USER_NOT_FOUND_MESSAGE_ERROR);
        }

        return users.getFirst();
    }


    /**
     * Validate book.
     *
     * @param bookId the book id
     * @return the book
     */
    private Book validateBook(final Integer bookId) {

        final var bookSearchCriteriaCommand = BookSearchCriteriaCommand.builder()
                .id(bookId)
                .build();
        final var books = this.bookRetrievalPersistencePort.find(bookSearchCriteriaCommand);

        if (CollectionUtils.isEmpty(books)) {
            throw new BookException(
                    ExceptionMessageConstants.BOOK_NOT_FOUND_CODE_ERROR,
                    ExceptionMessageConstants.BOOK_NOT_FOUND_MESSAGE_ERROR);
        }

        return books.getFirst();
    }
}
