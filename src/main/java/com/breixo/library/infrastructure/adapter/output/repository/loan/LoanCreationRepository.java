package com.breixo.library.infrastructure.adapter.output.repository.loan;

import com.breixo.library.domain.command.loan.CreateLoanCommand;
import com.breixo.library.domain.command.loan.LoanSearchCriteriaCommand;
import com.breixo.library.domain.exception.LoanException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.loan.Loan;
import com.breixo.library.domain.port.output.loan.LoanCreationPersistencePort;
import com.breixo.library.infrastructure.adapter.output.entities.LoanEntity;
import com.breixo.library.infrastructure.adapter.output.mapper.loan.LoanEntityMapper;
import com.breixo.library.infrastructure.adapter.output.mybatis.LoanMyBatisMapper;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** The Class Loan Creation repository. */
@Component
@RequiredArgsConstructor
public class LoanCreationRepository implements LoanCreationPersistencePort {

    /** The loan my batis mapper. */
    private final LoanMyBatisMapper loanMyBatisMapper;

    /** The loan entity mapper. */
    private final LoanEntityMapper loanEntityMapper;

    /** {@inheritDoc} */
    @Override
    public Loan execute(@Valid @NotNull final CreateLoanCommand createLoanCommand) {
        final var loanEntity = this.insert(createLoanCommand);
        return this.find(loanEntity.getId());
    }

    /**
     * Insert.
     *
     * @param createLoanCommand the create loan command
     * @return the loan entity
     */
    private LoanEntity insert(final CreateLoanCommand createLoanCommand) {

        final var loanEntity = this.loanEntityMapper.toLoanEntity(createLoanCommand);

        try {
            this.loanMyBatisMapper.insert(loanEntity);

        } catch (final Exception exception) {
            throw new LoanException(
                    ExceptionMessageConstants.LOAN_CREATION_ERROR_CODE_ERROR,
                    ExceptionMessageConstants.LOAN_CREATION_ERROR_MESSAGE_ERROR);
        }
        return loanEntity;
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
