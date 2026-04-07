package com.breixo.library.infrastructure.adapter.input.web.controller.loanrequest;

import com.breixo.library.domain.port.output.loanrequest.LoanRequestDeletionPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.api.DeleteLoanRequestV1Api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/** The Class Delete Loan Request Controller. */
@RestController
@RequiredArgsConstructor
public class DeleteLoanRequestController implements DeleteLoanRequestV1Api {

    /** The loan request deletion persistence port. */
    private final LoanRequestDeletionPersistencePort loanRequestDeletionPersistencePort;

    /** {@inheritDoc} */
    @Override
    public ResponseEntity<Void> deleteLoanRequestV1(final Integer id, final Integer xRequesterId) {

        // TODO: In the future, proxy through a Use Case to validate the xRequesterId permissions before deletion
        this.loanRequestDeletionPersistencePort.execute(id);

        return ResponseEntity.noContent().build();
    }
}
