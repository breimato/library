package com.breixo.library.infrastructure.adapter.output.repository.loanrequest;

import java.util.List;

import com.breixo.library.domain.command.loanrequest.LoanRequestSearchCriteriaCommand;
import com.breixo.library.domain.exception.LoanRequestException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.loanrequest.LoanRequest;
import com.breixo.library.domain.port.output.loanrequest.LoanRequestRetrievalPersistencePort;
import com.breixo.library.infrastructure.adapter.output.mapper.LoanRequestEntityMapper;
import com.breixo.library.infrastructure.adapter.output.mybatis.LoanRequestMyBatisMapper;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
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
    public LoanRequest findById(@NotNull final Integer id) {

        final var searchCriteria = LoanRequestSearchCriteriaCommand.builder().id(id).build();
        final var loanRequestList = this.find(searchCriteria);

        if (CollectionUtils.isEmpty(loanRequestList)) {
            throw new LoanRequestException(
                    ExceptionMessageConstants.LOAN_REQUEST_NOT_FOUND_CODE_ERROR,
                    ExceptionMessageConstants.LOAN_REQUEST_NOT_FOUND_MESSAGE_ERROR);
        }

        return loanRequestList.getFirst();
    }

    /** {@inheritDoc} */
    @Override
    public List<LoanRequest> find(@Valid @NotNull final LoanRequestSearchCriteriaCommand loanRequestSearchCriteriaCommand) {

        final var loanRequestEntities = this.loanRequestMyBatisMapper.find(loanRequestSearchCriteriaCommand);

        return this.loanRequestEntityMapper.toLoanRequestList(loanRequestEntities);
    }
}
