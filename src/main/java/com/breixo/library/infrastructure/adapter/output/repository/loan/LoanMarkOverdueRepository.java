package com.breixo.library.infrastructure.adapter.output.repository.loan;

import com.breixo.library.domain.port.output.loan.LoanMarkOverduePersistencePort;
import com.breixo.library.infrastructure.adapter.output.mybatis.LoanMyBatisMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** The Class Loan Mark Overdue Repository. */
@Component
@RequiredArgsConstructor
public class LoanMarkOverdueRepository implements LoanMarkOverduePersistencePort {

    /** The loan my batis mapper. */
    private final LoanMyBatisMapper loanMyBatisMapper;

    /** {@inheritDoc} */
    @Override
    public int markOverdue() {
        return this.loanMyBatisMapper.markOverdue();
    }
}
