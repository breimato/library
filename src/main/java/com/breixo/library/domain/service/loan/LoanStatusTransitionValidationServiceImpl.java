package com.breixo.library.domain.service.loan;

import java.util.EnumSet;

import com.breixo.library.domain.exception.LoanException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.loan.enums.LoanStatus;
import com.breixo.library.domain.port.input.loan.LoanStatusTransitionValidationService;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;

/** The Class Loan Status Transition Validation Service Impl. */
@Service
public class LoanStatusTransitionValidationServiceImpl implements LoanStatusTransitionValidationService {

    /** {@inheritDoc} */
    @Override
    public void execute(final LoanStatus currentStatus, final LoanStatus newStatus) {

        if (LoanStatus.RETURNED.equals(currentStatus) && BooleanUtils.isFalse(LoanStatus.RETURNED.equals(newStatus))) {
            throw new LoanException(
                    ExceptionMessageConstants.LOAN_ALREADY_RETURNED_CODE_ERROR,
                    ExceptionMessageConstants.LOAN_ALREADY_RETURNED_MESSAGE_ERROR);
        }

        final var isValid = switch (currentStatus) {

            case ACTIVE, OVERDUE -> EnumSet.of(LoanStatus.ACTIVE, LoanStatus.RETURNED, LoanStatus.OVERDUE).contains(newStatus);

            case RETURNED -> LoanStatus.RETURNED.equals(newStatus);
        };

        if (BooleanUtils.isFalse(isValid)) {
            throw new LoanException(
                    ExceptionMessageConstants.LOAN_INVALID_STATUS_TRANSITION_CODE_ERROR,
                    ExceptionMessageConstants.LOAN_INVALID_STATUS_TRANSITION_MESSAGE_ERROR);
        }
    }
}
