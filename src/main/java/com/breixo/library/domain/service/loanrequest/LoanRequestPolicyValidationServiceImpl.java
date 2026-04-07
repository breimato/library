package com.breixo.library.domain.service.loanrequest;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.breixo.library.domain.command.fine.FineSearchCriteriaCommand;
import com.breixo.library.domain.command.loan.LoanSearchCriteriaCommand;
import com.breixo.library.domain.command.loanrequest.CreateLoanRequestCommand;
import com.breixo.library.domain.exception.LoanRequestException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.fine.enums.FineStatus;
import com.breixo.library.domain.model.loan.enums.LoanStatus;
import com.breixo.library.domain.model.loanrequest.enums.LoanRequestStatus;
import com.breixo.library.domain.model.user.enums.UserRole;
import com.breixo.library.domain.port.input.loanrequest.LoanRequestPolicyValidationService;
import com.breixo.library.domain.port.input.user.AuthorizationService;
import com.breixo.library.domain.port.output.book.BookRetrievalPersistencePort;
import com.breixo.library.domain.port.output.fine.FineRetrievalPersistencePort;
import com.breixo.library.domain.port.output.loan.LoanRetrievalPersistencePort;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

/** The Class Loan Request Policy Validation Service Impl. */
@Service
@RequiredArgsConstructor
public class LoanRequestPolicyValidationServiceImpl implements LoanRequestPolicyValidationService {

    /** The Constant MAX_ACTIVE_LOANS. */
    private static final int MAX_ACTIVE_LOANS = 3;

    /** The Constant MANAGER_ONLY_STATUSES. */
    private static final Set<LoanRequestStatus> MANAGER_ONLY_STATUSES = Set.of(
            LoanRequestStatus.APPROVED, LoanRequestStatus.REJECTED);

    /** The authorization service. */
    private final AuthorizationService authorizationService;

    /** The fine retrieval persistence port. */
    private final FineRetrievalPersistencePort fineRetrievalPersistencePort;

    /** The loan retrieval persistence port. */
    private final LoanRetrievalPersistencePort loanRetrievalPersistencePort;

    /** The book retrieval persistence port. */
    private final BookRetrievalPersistencePort bookRetrievalPersistencePort;

    /** {@inheritDoc} */
    @Override
    public void validateCreation(final CreateLoanRequestCommand createLoanRequestCommand) {

        final var userId = createLoanRequestCommand.userId();

        this.validatePendingFines(userId);
        this.validateActiveLoansLimit(userId);
        this.validateBookAvailability(createLoanRequestCommand.bookId());
    }

    /** {@inheritDoc} */
    @Override
    public void validateTransitionAuthorization(@NotNull final Integer requesterId,
            @NotNull final Integer resourceOwnerId, @NotNull final LoanRequestStatus newStatus) {

        Map.of(
                true,  (Runnable) () -> this.authorizationService.requireMinimumRole(requesterId, UserRole.MANAGER),
                false, (Runnable) () -> this.authorizationService.requireOwnResourceOrRole(requesterId, resourceOwnerId, UserRole.MANAGER)
        ).get(MANAGER_ONLY_STATUSES.contains(newStatus)).run();
    }

    /**
     * Validate pending fines.
     *
     * @param userId the user id
     */
    private void validatePendingFines(final Integer userId) {

        final var fineSearchCriteriaCommand = FineSearchCriteriaCommand.builder()
                .userId(userId)
                .statusId(FineStatus.PENDING.getId())
                .build();

        final var fineList = this.fineRetrievalPersistencePort.find(fineSearchCriteriaCommand);

        if (CollectionUtils.isNotEmpty(fineList)) {
            throw new LoanRequestException(
                    ExceptionMessageConstants.LOAN_REQUEST_USER_HAS_PENDING_FINES_CODE_ERROR,
                    ExceptionMessageConstants.LOAN_REQUEST_USER_HAS_PENDING_FINES_MESSAGE_ERROR);
        }
    }

    /**
     * Validate active loans limit.
     *
     * @param userId the user id
     */
    private void validateActiveLoansLimit(final Integer userId) {

        final var loanSearchCriteriaCommand = LoanSearchCriteriaCommand.builder()
                .userId(userId)
                .statusId(LoanStatus.ACTIVE.getId())
                .build();

        final var loanList = this.loanRetrievalPersistencePort.find(loanSearchCriteriaCommand);

        final var loanListCount = Optional.ofNullable(loanList).map(java.util.List::size).orElse(0);

        if (loanListCount >= MAX_ACTIVE_LOANS) {
            throw new LoanRequestException(
                    ExceptionMessageConstants.LOAN_REQUEST_USER_ACTIVE_LOANS_LIMIT_REACHED_CODE_ERROR,
                    ExceptionMessageConstants.LOAN_REQUEST_USER_ACTIVE_LOANS_LIMIT_REACHED_MESSAGE_ERROR);
        }
    }

    /**
     * Validate book availability.
     *
     * @param bookId the book id
     */
    private void validateBookAvailability(final Integer bookId) {

        final var book = this.bookRetrievalPersistencePort.findById(bookId);

        if (book.availableCopies() > 0) {
            throw new LoanRequestException(
                    ExceptionMessageConstants.LOAN_REQUEST_BOOK_AVAILABLE_CODE_ERROR,
                    ExceptionMessageConstants.LOAN_REQUEST_BOOK_AVAILABLE_MESSAGE_ERROR);
        }
    }
}
