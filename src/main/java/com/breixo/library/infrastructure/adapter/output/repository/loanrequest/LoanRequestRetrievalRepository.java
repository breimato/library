package com.breixo.library.infrastructure.adapter.output.repository.loanrequest;

import java.util.List;
import java.util.Optional;

import com.breixo.library.domain.command.loanrequest.LoanRequestSearchCriteriaCommand;
import com.breixo.library.domain.model.loanrequest.LoanRequest;
import com.breixo.library.domain.port.output.loanrequest.LoanRequestRetrievalPersistencePort;
import com.breixo.library.infrastructure.adapter.output.mapper.loanrequest.LoanRequestEntityMapper;
import com.breixo.library.infrastructure.adapter.output.mybatis.LoanRequestMyBatisMapper;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** The Class Loan Request Retrieval Repository. */
@Component
@RequiredArgsConstructor
public class LoanRequestRetrievalRepository implements LoanRequestRetrievalPersistencePort {

    /** The loan request my batis mapper. */
    private final LoanRequestMyBatisMapper loanRequestMyBatisMapper;

    /** The loan request entity mapper. */
    private final LoanRequestEntityMapper loanRequestEntityMapper;

    /** {@inheritDoc} */
    @Override
    public Optional<LoanRequest> findById(@NotNull final Integer id) {

        final var searchCriteria = LoanRequestSearchCriteriaCommand.builder().id(id).build();
        final var loanRequestList = this.find(searchCriteria);

        return loanRequestList.isEmpty() ? Optional.empty() : Optional.of(loanRequestList.getFirst());
    }

    /** {@inheritDoc} */
    @Override
    public List<LoanRequest> find(@Valid @NotNull final LoanRequestSearchCriteriaCommand loanRequestSearchCriteriaCommand) {

        final var loanRequestEntities = this.loanRequestMyBatisMapper.find(loanRequestSearchCriteriaCommand);

        return this.loanRequestEntityMapper.toLoanRequestList(loanRequestEntities);
    }
}
