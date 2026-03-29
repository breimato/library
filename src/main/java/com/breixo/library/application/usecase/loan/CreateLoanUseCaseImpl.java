package com.breixo.library.application.usecase.loan;

import com.breixo.library.domain.command.book.BookSearchCriteriaCommand;
import com.breixo.library.domain.command.loan.CreateLoanCommand;
import com.breixo.library.domain.command.loan.LoanSearchCriteriaCommand;
import com.breixo.library.domain.command.user.UserSearchCriteriaCommand;
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
import com.breixo.library.domain.service.LoanPolicyValidationService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
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
        final var loanList = this.loanRetrievalPersistencePort.findAll(loanSearchCriteriaCommand);

        this.loanPolicyValidationService.checkCanBorrow(user, book, loanList);

        return this.loanCreationPersistencePort.execute(createLoanCommand);
    }


    /**
     * Validate user.
     *
     * @param userId the user id
     * @return the user
     */
    private User validateUser(final Integer userId) {

        final var userSearchCriteriaCommand = UserSearchCriteriaCommand.builder().id(userId).build();

        return this.userRetrievalPersistencePort.find(userSearchCriteriaCommand)
                .orElseThrow(() -> new UserException(
                        ExceptionMessageConstants.USER_NOT_FOUND_CODE_ERROR,
                        ExceptionMessageConstants.USER_NOT_FOUND_MESSAGE_ERROR));
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

        return this.bookRetrievalPersistencePort.find(bookSearchCriteriaCommand)
                .orElseThrow(() -> new BookException(
                        ExceptionMessageConstants.BOOK_NOT_FOUND_CODE_ERROR,
                        ExceptionMessageConstants.BOOK_NOT_FOUND_MESSAGE_ERROR));
    }
}
