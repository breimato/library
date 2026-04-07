package com.breixo.library.application.event.loanrequest;

import java.time.LocalDate;

import com.breixo.library.domain.command.loan.CreateLoanCommand;
import com.breixo.library.domain.event.loanrequest.LoanRequestApprovedDomainEvent;
import com.breixo.library.domain.port.input.loan.CreateLoanUseCase;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/** The Class Loan Creation On Request Approved Event Listener. */
@Component
@RequiredArgsConstructor
public class LoanCreationOnRequestApprovedEventListener {

    /** The Constant LOAN_DURATION_WEEKS. */
    private static final int LOAN_DURATION_WEEKS = 2;

    /** The create loan use case. */
    private final CreateLoanUseCase createLoanUseCase;

    /**
     * Handle.
     *
     * @param loanRequestApprovedDomainEvent the loan request approved domain event
     */
    @EventListener
    public void handle(final LoanRequestApprovedDomainEvent loanRequestApprovedDomainEvent) {

        final var createLoanCommand = CreateLoanCommand.builder()
                .userId(loanRequestApprovedDomainEvent.userId())
                .bookId(loanRequestApprovedDomainEvent.bookId())
                .dueDate(LocalDate.now().plusWeeks(LOAN_DURATION_WEEKS))
                .build();

        this.createLoanUseCase.execute(createLoanCommand);
    }
}
