package com.breixo.library.domain.service.user;

import java.util.List;

import com.breixo.library.domain.port.input.user.UserPolicyValidationService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import com.breixo.library.domain.command.fine.FineSearchCriteriaCommand;
import com.breixo.library.domain.exception.UserException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.fine.enums.FineStatus;
import com.breixo.library.domain.model.loan.Loan;
import com.breixo.library.domain.model.user.User;
import com.breixo.library.domain.model.user.enums.UserStatus;
import com.breixo.library.domain.port.output.fine.FineRetrievalPersistencePort;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

/** The Class User Policy Validation Service Impl. */
@Service
@RequiredArgsConstructor
public class UserPolicyValidationServiceImpl implements UserPolicyValidationService {

    /** The fine retrieval persistence port. */
    private final FineRetrievalPersistencePort fineRetrievalPersistencePort;

    /** {@inheritDoc} */
    @Override
    public void check(@NotNull final User user, @NotNull final List<Loan> loanList) {
        this.validateIsActive(user);
        this.validateHasNoPendingFines(loanList);
    }

    /**
     * Validate is active.
     *
     * @param user the user
     */
    private void validateIsActive(final User user) {

        if (UserStatus.BLOCKED.equals(user.status())) {
            throw new UserException(
                    ExceptionMessageConstants.USER_BLOCKED_CODE_ERROR,
                    ExceptionMessageConstants.USER_BLOCKED_MESSAGE_ERROR);
        }

        if (UserStatus.SUSPENDED.equals(user.status())) {
            throw new UserException(
                    ExceptionMessageConstants.USER_SUSPENDED_CODE_ERROR,
                    ExceptionMessageConstants.USER_SUSPENDED_MESSAGE_ERROR);
        }
    }

    /**
     * Validate has no pending fines.
     *
     * @param loanList the loan list
     */
    private void validateHasNoPendingFines(final List<Loan> loanList) {

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
            throw new UserException(
                    ExceptionMessageConstants.USER_HAS_PENDING_FINES_CODE_ERROR,
                    ExceptionMessageConstants.USER_HAS_PENDING_FINES_MESSAGE_ERROR);
        }
    }
}
