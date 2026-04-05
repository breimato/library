package com.breixo.library.infrastructure.adapter.input.web.controller.loan;

import com.breixo.library.domain.port.output.loan.LoanDeletionPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.api.DeleteLoanV1Api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

/** The Class Delete Loan Controller. */
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('MANAGER')")
public class DeleteLoanController implements DeleteLoanV1Api {

    /** The loan deletion persistence port. */
    private final LoanDeletionPersistencePort loanDeletionPersistencePort;

    /** {@inheritDoc} */
    @Override
    public ResponseEntity<Void> deleteLoanV1(final Integer id) {

        this.loanDeletionPersistencePort.execute(id);

        return ResponseEntity.noContent().build();
    }
}
