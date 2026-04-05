package com.breixo.library.infrastructure.adapter.input.web.controller.loanrequest;

import com.breixo.library.domain.command.loanrequest.UpdateLoanRequestCommand;
import com.breixo.library.domain.port.input.loanrequest.UpdateLoanRequestUseCase;
import com.breixo.library.infrastructure.adapter.input.web.api.PatchLoanRequestV1Api;
import com.breixo.library.infrastructure.adapter.input.web.dto.LoanRequestV1Response;
import com.breixo.library.infrastructure.adapter.input.web.dto.PatchLoanRequestV1Request;
import com.breixo.library.infrastructure.adapter.input.web.mapper.loanrequest.LoanRequestResponseMapper;
import com.breixo.library.infrastructure.adapter.input.web.mapper.loanrequest.PatchLoanRequestRequestMapper;
import com.breixo.library.infrastructure.adapter.input.web.security.AuthenticatedUserContextHelper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/** The Class Patch Loan Request Controller. */
@RestController
@RequiredArgsConstructor
public class PatchLoanRequestController implements PatchLoanRequestV1Api {

    /** The update loan request use case. */
    private final UpdateLoanRequestUseCase updateLoanRequestUseCase;

    /** The patch loan request request mapper. */
    private final PatchLoanRequestRequestMapper patchLoanRequestRequestMapper;

    /** The loan request response mapper. */
    private final LoanRequestResponseMapper loanRequestResponseMapper;

    /** {@inheritDoc} */
    @Override
    public ResponseEntity<LoanRequestV1Response> patchLoanRequestV1(
            final Integer id,
            final PatchLoanRequestV1Request patchLoanRequestV1Request) {

        final var baseCommand =
                this.patchLoanRequestRequestMapper.toUpdateLoanRequestCommand(id, patchLoanRequestV1Request);

        final var updateLoanRequestCommand = UpdateLoanRequestCommand.builder()
                .id(baseCommand.id())
                .status(baseCommand.status())
                .rejectionReason(baseCommand.rejectionReason())
                .authenticatedUserId(AuthenticatedUserContextHelper.getAuthenticatedUserId())
                .authenticatedUserRole(AuthenticatedUserContextHelper.getAuthenticatedUserRole())
                .build();

        final var loanRequest = this.updateLoanRequestUseCase.execute(updateLoanRequestCommand);

        final var loanRequestV1Response = this.loanRequestResponseMapper.toLoanRequestV1Response(loanRequest);

        return ResponseEntity.ok(loanRequestV1Response);
    }
}
