package com.breixo.library.infrastructure.adapter.output.repository.loan;

import com.breixo.library.domain.command.loan.LoanSearchCriteriaCommand;
import com.breixo.library.domain.command.loan.UpdateLoanRenewCommand;
import com.breixo.library.domain.exception.LoanException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.loan.Loan;
import com.breixo.library.domain.port.output.loan.LoanRenewPersistencePort;
import com.breixo.library.infrastructure.adapter.output.mapper.loan.LoanEntityMapper;
import com.breixo.library.infrastructure.adapter.output.mybatis.LoanMyBatisMapper;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** The Class Loan Renew Repository. */
@Component
@RequiredArgsConstructor
public class LoanRenewRepository implements LoanRenewPersistencePort {

    /** The loan my batis mapper. */
    private final LoanMyBatisMapper loanMyBatisMapper;

    /** The loan entity mapper. */
    private final LoanEntityMapper loanEntityMapper;

    /** {@inheritDoc} */
    @Override
    public Loan execute(@Valid @NotNull final UpdateLoanRenewCommand updateLoanRenewCommand) {
        this.renew(updateLoanRenewCommand);
        return this.find(updateLoanRenewCommand.id());
    }

    /**
     * Renew.
     *
     * @param updateLoanRenewCommand the update loan renew command
     */
    private void renew(final UpdateLoanRenewCommand updateLoanRenewCommand) {

        try {
            this.loanMyBatisMapper.renew(updateLoanRenewCommand);

        } catch (final Exception exception) {
            throw new LoanException(
                    ExceptionMessageConstants.LOAN_UPDATE_ERROR_CODE_ERROR,
                    ExceptionMessageConstants.LOAN_UPDATE_ERROR_MESSAGE_ERROR);
        }
    }

    /**
     * Find.
     *
     * @param id the id
     * @return the loan
     */
    private Loan find(final Integer id) {

        final var loanSearchCriteriaCommand = LoanSearchCriteriaCommand.builder().id(id).build();

        final var loanEntity = this.loanMyBatisMapper.find(loanSearchCriteriaCommand).getFirst();

        return this.loanEntityMapper.toLoan(loanEntity);
    }
}
