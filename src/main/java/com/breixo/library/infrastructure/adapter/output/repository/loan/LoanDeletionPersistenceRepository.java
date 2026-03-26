package com.breixo.library.infrastructure.adapter.output.repository.loan;

import com.breixo.library.domain.port.output.loan.LoanDeletionPersistencePort;
import com.breixo.library.infrastructure.adapter.output.mybatis.LoanMyBatisMapper;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** The Class Loan Deletion Persistence Repository. */
@Component
@RequiredArgsConstructor
public class LoanDeletionPersistenceRepository implements LoanDeletionPersistencePort {

    /** The loan my batis mapper. */
    private final LoanMyBatisMapper loanMyBatisMapper;

    /** {@inheritDoc} */
    @Override
    public void execute(@NotNull final Integer id) {
        this.loanMyBatisMapper.delete(id);
    }
}
