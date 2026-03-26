package com.breixo.library.application.usecase.loan;

import com.breixo.library.domain.command.book.BookSearchCriteriaCommand;
import com.breixo.library.domain.command.loan.CreateLoanCommand;
import com.breixo.library.domain.command.user.UserSearchCriteriaCommand;
import com.breixo.library.domain.exception.BookException;
import com.breixo.library.domain.exception.UserException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.loan.Loan;
import com.breixo.library.domain.port.input.loan.CreateLoanUseCase;
import com.breixo.library.domain.port.output.book.BookRetrievalPersistencePort;
import com.breixo.library.domain.port.output.loan.LoanCreationPersistencePort;
import com.breixo.library.domain.port.output.user.UserRetrievalPersistencePort;
import com.breixo.library.domain.service.LoanPolicyValidationService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Component;

/** The Class Create Loan Use Case Impl. */
@Component
@RequiredArgsConstructor
public class CreateLoanUseCaseImpl implements CreateLoanUseCase {

    /** The user retrieval persistence port. */
    private final UserRetrievalPersistencePort userRetrievalPersistencePort;

    /** The book retrieval persistence port. */
    private final BookRetrievalPersistencePort bookRetrievalPersistencePort;

    /** The loan policy validation service. */
    private final LoanPolicyValidationService loanPolicyValidationService;

    /** The loan creation persistence port. */
    private final LoanCreationPersistencePort loanCreationPersistencePort;

    /** {@inheritDoc} */
    @Override
    public Loan execute(@Valid @NotNull final CreateLoanCommand createLoanCommand) {
        final var userSearchCriteriaCommand = UserSearchCriteriaCommand.builder()
                .id(createLoanCommand.userId())
                .build();
        final var user = this.userRetrievalPersistencePort.find(userSearchCriteriaCommand)
                .orElseThrow(() -> new UserException(
                        ExceptionMessageConstants.USER_NOT_FOUND_CODE_ERROR,
                        ExceptionMessageConstants.USER_NOT_FOUND_MESSAGE_ERROR));

        final var bookSearchCriteriaCommand = BookSearchCriteriaCommand.builder()
                .id(createLoanCommand.bookId())
                .build();
        final var book = this.bookRetrievalPersistencePort.find(bookSearchCriteriaCommand)
                .orElseThrow(() -> new BookException(
                        ExceptionMessageConstants.BOOK_NOT_FOUND_CODE_ERROR,
                        ExceptionMessageConstants.BOOK_NOT_FOUND_MESSAGE_ERROR));

        this.loanPolicyValidationService.checkCanBorrow(user, book);

        return this.loanCreationPersistencePort.execute(createLoanCommand);
    }
}
