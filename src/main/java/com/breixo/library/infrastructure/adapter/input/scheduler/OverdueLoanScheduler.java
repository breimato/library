package com.breixo.library.infrastructure.adapter.input.scheduler;

import com.breixo.library.domain.port.output.loan.LoanMarkOverduePersistencePort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/** The Class Overdue Loan Scheduler. */
@Slf4j
@Component
@RequiredArgsConstructor
public class OverdueLoanScheduler {

    /** The loan mark overdue persistence port. */
    private final LoanMarkOverduePersistencePort loanMarkOverduePersistencePort;

    /**
     * Mark overdue loans.
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void markOverdueLoans() {

        final var updatedCount = this.loanMarkOverduePersistencePort.markOverdue();

        log.info("Overdue loans marked: {}", updatedCount);
    }
}
