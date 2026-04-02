package com.breixo.library.infrastructure.adapter.output.repository.loanrequest;

import com.breixo.library.domain.port.output.loanrequest.LoanRequestDeletionPersistencePort;
import com.breixo.library.infrastructure.adapter.output.mybatis.LoanRequestMyBatisMapper;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** The Class Loan Request Deletion Repository. */
@Component
@RequiredArgsConstructor
public class LoanRequestDeletionRepository implements LoanRequestDeletionPersistencePort {

    /** The loan request my batis mapper. */
    private final LoanRequestMyBatisMapper loanRequestMyBatisMapper;

    /** {@inheritDoc} */
    @Override
    public void execute(@NotNull final Integer id) {

        this.loanRequestMyBatisMapper.delete(id);
    }
}
