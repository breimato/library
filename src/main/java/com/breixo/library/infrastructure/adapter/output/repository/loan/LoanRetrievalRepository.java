package com.breixo.library.infrastructure.adapter.output.repository.loan;

import java.util.List;

import com.breixo.library.domain.command.loan.LoanSearchCriteriaCommand;
import com.breixo.library.domain.model.loan.Loan;
import com.breixo.library.domain.port.output.loan.LoanRetrievalPersistencePort;
import com.breixo.library.infrastructure.adapter.output.mapper.loan.LoanEntityMapper;
import com.breixo.library.infrastructure.adapter.output.mybatis.LoanMyBatisMapper;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** The Class Loan Retrieval repository. */
@Component
@RequiredArgsConstructor
public class LoanRetrievalRepository implements LoanRetrievalPersistencePort {

    /** The loan my batis mapper. */
    private final LoanMyBatisMapper loanMyBatisMapper;

    /** The loan entity mapper. */
    private final LoanEntityMapper loanEntityMapper;

    /** {@inheritDoc} */
    @Override
    public List<Loan> find(@Valid @NotNull final LoanSearchCriteriaCommand loanSearchCriteriaCommand) {

        final var loanEntities = this.loanMyBatisMapper.find(loanSearchCriteriaCommand);

        return this.loanEntityMapper.toLoanList(loanEntities);
    }

    /** {@inheritDoc} */
    @Override
    public List<Loan> findByUserId(@NotNull final Integer userId) {

        final var loanSearchCriteriaCommand = LoanSearchCriteriaCommand.builder()
                .userId(userId)
                .build();

        return this.find(loanSearchCriteriaCommand);
    }
}
