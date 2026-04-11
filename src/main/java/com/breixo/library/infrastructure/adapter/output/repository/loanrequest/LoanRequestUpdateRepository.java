package com.breixo.library.infrastructure.adapter.output.repository.loanrequest;

import com.breixo.library.domain.command.loanrequest.LoanRequestSearchCriteriaCommand;
import com.breixo.library.domain.command.loanrequest.UpdateLoanRequestCommand;
import com.breixo.library.domain.exception.LoanRequestException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.loanrequest.LoanRequest;
import com.breixo.library.domain.port.output.loanrequest.LoanRequestUpdatePersistencePort;
import com.breixo.library.infrastructure.adapter.output.mapper.loanrequest.LoanRequestEntityMapper;
import com.breixo.library.infrastructure.adapter.output.mybatis.LoanRequestMyBatisMapper;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** The Class Loan Request Update Repository. */
@Component
@RequiredArgsConstructor
public class LoanRequestUpdateRepository implements LoanRequestUpdatePersistencePort {

    /** The loan request my batis mapper. */
    private final LoanRequestMyBatisMapper loanRequestMyBatisMapper;

    /** The loan request entity mapper. */
    private final LoanRequestEntityMapper loanRequestEntityMapper;

    /** {@inheritDoc} */
    @Override
    public LoanRequest execute(@Valid @NotNull final UpdateLoanRequestCommand updateLoanRequestCommand) {
        this.update(updateLoanRequestCommand);
        return this.find(updateLoanRequestCommand.id());
    }

    /**
     * Update.
     *
     * @param updateLoanRequestCommand the update loan request command.
     */
    private void update(final UpdateLoanRequestCommand updateLoanRequestCommand) {

        final var loanRequestEntity = this.loanRequestEntityMapper.toLoanRequestEntity(updateLoanRequestCommand);

        try {
            this.loanRequestMyBatisMapper.update(loanRequestEntity);

        } catch (final Exception exception) {
            throw new LoanRequestException(
                    ExceptionMessageConstants.LOAN_REQUEST_UPDATE_ERROR_CODE_ERROR,
                    ExceptionMessageConstants.LOAN_REQUEST_UPDATE_ERROR_MESSAGE_ERROR);
        }
    }

    /**
     * Find.
     *
     * @param id the loan request identifier.
     * @return the loan request.
     */
    private LoanRequest find(final Integer id) {

        final var searchCriteria = LoanRequestSearchCriteriaCommand.builder().id(id).build();

        final var loanRequestEntity = this.loanRequestMyBatisMapper.find(searchCriteria).getFirst();

        return this.loanRequestEntityMapper.toLoanRequest(loanRequestEntity);
    }
}
