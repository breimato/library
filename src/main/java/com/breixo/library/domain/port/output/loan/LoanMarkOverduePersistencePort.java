package com.breixo.library.domain.port.output.loan;

/** The Interface Loan Mark Overdue Persistence Port. */
public interface LoanMarkOverduePersistencePort {

    /**
     * Mark overdue.
     *
     * @return the number of loans marked as overdue.
     */
    int markOverdue();
}
