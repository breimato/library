package com.breixo.library.infrastructure.adapter.output.repository.loanrequest;

import com.breixo.library.domain.command.loanrequest.CreateLoanRequestCommand;
import com.breixo.library.domain.command.loanrequest.LoanRequestSearchCriteriaCommand;
import com.breixo.library.domain.exception.LoanRequestException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.loanrequest.LoanRequest;
import com.breixo.library.domain.port.output.loanrequest.LoanRequestCreationPersistencePort;
import com.breixo.library.infrastructure.adapter.output.entities.LoanRequestEntity;
import com.breixo.library.infrastructure.adapter.output.mapper.loanrequest.LoanRequestEntityMapper;
import com.breixo.library.infrastructure.adapter.output.mybatis.LoanRequestMyBatisMapper;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** The Class Loan Request Creation Repository. */
@Component
@RequiredArgsConstructor
public class LoanRequestCreationRepository implements LoanRequestCreationPersistencePort {

    /** The loan request my batis mapper. */
    private final LoanRequestMyBatisMapper loanRequestMyBatisMapper;

    /** The loan request entity mapper. */
    private final LoanRequestEntityMapper loanRequestEntityMapper;

    /** {@inheritDoc} */
    @Override
    public LoanRequest execute(@Valid @NotNull final CreateLoanRequestCommand createLoanRequestCommand) {
        final var loanRequestEntity = this.insert(createLoanRequestCommand);
        return this.find(loanRequestEntity.getId());
    }

    /**
     * Insert.
     *
     * @param createLoanRequestCommand the create loan request command.
     * @return the loan request entity.
     */
    private LoanRequestEntity insert(final CreateLoanRequestCommand createLoanRequestCommand) {

        final var loanRequestEntity = this.loanRequestEntityMapper.toLoanRequestEntity(createLoanRequestCommand);

        try {
            this.loanRequestMyBatisMapper.insert(loanRequestEntity);

        } catch (final Exception exception) {
            throw new LoanRequestException(
                    ExceptionMessageConstants.LOAN_REQUEST_CREATION_ERROR_CODE_ERROR,
                    ExceptionMessageConstants.LOAN_REQUEST_CREATION_ERROR_MESSAGE_ERROR);
        }
        return loanRequestEntity;
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
