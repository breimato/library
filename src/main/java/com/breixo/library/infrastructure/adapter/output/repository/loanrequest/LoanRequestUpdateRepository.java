package com.breixo.library.infrastructure.adapter.output.repository.loanrequest;

import com.breixo.library.domain.command.loanrequest.LoanRequestSearchCriteriaCommand;
import com.breixo.library.domain.command.loanrequest.UpdateLoanRequestCommand;
import com.breixo.library.domain.model.loanrequest.LoanRequest;
import com.breixo.library.domain.port.output.loanrequest.LoanRequestUpdatePersistencePort;
import com.breixo.library.infrastructure.adapter.output.mapper.LoanRequestEntityMapper;
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

        final var loanRequestEntity = this.loanRequestEntityMapper.toLoanRequestEntity(updateLoanRequestCommand);

        this.loanRequestMyBatisMapper.update(loanRequestEntity);

        final var searchCriteria = LoanRequestSearchCriteriaCommand.builder().id(loanRequestEntity.getId()).build();
        final var updatedEntity = this.loanRequestMyBatisMapper.find(searchCriteria).getFirst();

        return this.loanRequestEntityMapper.toLoanRequest(updatedEntity);
    }
}
