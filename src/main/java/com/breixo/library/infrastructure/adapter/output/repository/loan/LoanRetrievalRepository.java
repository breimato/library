package com.breixo.library.infrastructure.adapter.output.repository.loan;

import java.util.List;
import java.util.Optional;

import com.breixo.library.domain.command.loan.LoanSearchCriteriaCommand;
import com.breixo.library.domain.model.loan.Loan;
import com.breixo.library.domain.port.output.loan.LoanRetrievalPersistencePort;
import com.breixo.library.infrastructure.adapter.output.mapper.LoanEntityMapper;
import com.breixo.library.infrastructure.adapter.output.mybatis.LoanMyBatisMapper;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
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
    public Optional<Loan> find(@Valid @NotNull final LoanSearchCriteriaCommand loanSearchCriteriaCommand) {
        final var loanEntities = this.loanMyBatisMapper.find(loanSearchCriteriaCommand);
        if (CollectionUtils.isEmpty(loanEntities)) {
            return Optional.empty();
        }
        final var loan = this.loanEntityMapper.toLoan(loanEntities.getFirst());
        return Optional.of(loan);
    }

    /** {@inheritDoc} */
    @Override
    public List<Loan> findAll(@Valid @NotNull final LoanSearchCriteriaCommand loanSearchCriteriaCommand) {
        final var loanEntities = this.loanMyBatisMapper.find(loanSearchCriteriaCommand);
        return this.loanEntityMapper.toLoanList(loanEntities);
    }
}
