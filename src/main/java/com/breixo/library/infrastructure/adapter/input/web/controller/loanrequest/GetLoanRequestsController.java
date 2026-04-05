package com.breixo.library.infrastructure.adapter.input.web.controller.loanrequest;

import com.breixo.library.domain.command.loanrequest.LoanRequestSearchCriteriaCommand;
import com.breixo.library.domain.model.user.enums.UserRole;
import com.breixo.library.domain.port.output.loanrequest.LoanRequestRetrievalPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.api.GetLoanRequestsV1Api;
import com.breixo.library.infrastructure.adapter.input.web.dto.GetLoanRequestsV1Request;
import com.breixo.library.infrastructure.adapter.input.web.dto.GetLoanRequestsV1Response;
import com.breixo.library.infrastructure.adapter.input.web.mapper.loanrequest.GetLoanRequestsV1RequestMapper;
import com.breixo.library.infrastructure.adapter.input.web.mapper.loanrequest.LoanRequestMapper;
import com.breixo.library.infrastructure.adapter.input.web.security.AuthenticatedUserContextHelper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/** The Class Get Loan Requests Controller. */
@RestController
@RequiredArgsConstructor
public class GetLoanRequestsController implements GetLoanRequestsV1Api {

    /** The loan request retrieval persistence port. */
    private final LoanRequestRetrievalPersistencePort loanRequestRetrievalPersistencePort;

    /** The get loan requests V1 request mapper. */
    private final GetLoanRequestsV1RequestMapper getLoanRequestsV1RequestMapper;

    /** The loan request mapper. */
    private final LoanRequestMapper loanRequestMapper;

    /** {@inheritDoc} */
    @Override
    public ResponseEntity<GetLoanRequestsV1Response> getLoanRequestsV1(final GetLoanRequestsV1Request getLoanRequestsV1Request) {

        var loanRequestSearchCriteriaCommand =
                this.getLoanRequestsV1RequestMapper.toLoanRequestSearchCriteriaCommand(getLoanRequestsV1Request);

        if (UserRole.NORMAL.equals(AuthenticatedUserContextHelper.getAuthenticatedUserRole())) {
            loanRequestSearchCriteriaCommand = LoanRequestSearchCriteriaCommand.builder()
                    .id(loanRequestSearchCriteriaCommand.getId())
                    .bookId(loanRequestSearchCriteriaCommand.getBookId())
                    .statusId(loanRequestSearchCriteriaCommand.getStatusId())
                    .userId(AuthenticatedUserContextHelper.getAuthenticatedUserId())
                    .build();
        }

        final var loanRequests = this.loanRequestRetrievalPersistencePort.find(loanRequestSearchCriteriaCommand);

        final var loanRequestV1List = this.loanRequestMapper.toLoanRequestV1List(loanRequests);

        final var getLoanRequestsV1Response = GetLoanRequestsV1Response.builder().loanRequests(loanRequestV1List).build();

        return ResponseEntity.ok(getLoanRequestsV1Response);
    }
}
