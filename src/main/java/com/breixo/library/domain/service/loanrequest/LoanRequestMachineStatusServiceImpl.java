package com.breixo.library.domain.service.loanrequest;

import java.util.EnumSet;

import com.breixo.library.domain.exception.LoanRequestException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.loanrequest.enums.LoanRequestStatus;
import com.breixo.library.domain.port.input.loanrequest.LoanRequestMachineStatusService;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;

/** The Class Loan Request Machine Status Service Impl. */
@Service
public class LoanRequestMachineStatusServiceImpl implements LoanRequestMachineStatusService {

    /** {@inheritDoc} */
    @Override
    public void execute(final LoanRequestStatus currentStatus, final LoanRequestStatus newStatus) {

        if (BooleanUtils.isFalse(LoanRequestStatus.PENDING.equals(currentStatus)) && BooleanUtils.isFalse(currentStatus.equals(newStatus))) {
            throw new LoanRequestException(
                    ExceptionMessageConstants.LOAN_REQUEST_INVALID_STATUS_TRANSITION_CODE_ERROR,
                    ExceptionMessageConstants.LOAN_REQUEST_INVALID_STATUS_TRANSITION_MESSAGE_ERROR);
        }

        final var isValid = switch (currentStatus) {

            case PENDING -> EnumSet.of(LoanRequestStatus.PENDING, LoanRequestStatus.APPROVED, LoanRequestStatus.REJECTED, LoanRequestStatus.CANCELLED).contains(newStatus);

            case APPROVED, REJECTED, CANCELLED -> currentStatus.equals(newStatus);
        };

        if (BooleanUtils.isFalse(isValid)) {
            throw new LoanRequestException(
                    ExceptionMessageConstants.LOAN_REQUEST_INVALID_STATUS_TRANSITION_CODE_ERROR,
                    ExceptionMessageConstants.LOAN_REQUEST_INVALID_STATUS_TRANSITION_MESSAGE_ERROR);
        }
    }
}
