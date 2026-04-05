package com.breixo.library.infrastructure.adapter.input.web.controller.loan;

import com.breixo.library.domain.command.loan.UpdateLoanRenewCommand;
import com.breixo.library.domain.port.input.loan.UpdateLoanRenewUseCase;
import com.breixo.library.infrastructure.adapter.input.web.api.PatchLoanRenewV1Api;
import com.breixo.library.infrastructure.adapter.input.web.dto.LoanV1ResponseDto;
import com.breixo.library.infrastructure.adapter.input.web.dto.PatchLoanRenewV1Request;
import com.breixo.library.infrastructure.adapter.input.web.mapper.loan.LoanResponseMapper;
import com.breixo.library.infrastructure.adapter.input.web.mapper.loan.PatchLoanRenewRequestMapper;
import com.breixo.library.infrastructure.adapter.input.web.security.AuthenticatedUserContextHelper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/** The Class Patch Loan Renew Controller. */
@RestController
@RequiredArgsConstructor
public class PatchLoanRenewController implements PatchLoanRenewV1Api {

    /** The update loan renew use case. */
    private final UpdateLoanRenewUseCase updateLoanRenewUseCase;

    /** The patch loan renew request mapper. */
    private final PatchLoanRenewRequestMapper patchLoanRenewRequestMapper;

    /** The loan response mapper. */
    private final LoanResponseMapper loanResponseMapper;

    /** {@inheritDoc} */
    @Override
    public ResponseEntity<LoanV1ResponseDto> patchLoanRenewV1(
            final Integer id, final PatchLoanRenewV1Request patchLoanRenewV1Request) {

        final var baseCommand = this.patchLoanRenewRequestMapper.toUpdateLoanRenewCommand(id, patchLoanRenewV1Request);

        final var updateLoanRenewCommand = UpdateLoanRenewCommand.builder()
                .id(baseCommand.id())
                .dueDate(baseCommand.dueDate())
                .authenticatedUserId(AuthenticatedUserContextHelper.getAuthenticatedUserId())
                .authenticatedUserRole(AuthenticatedUserContextHelper.getAuthenticatedUserRole())
                .build();

        final var loan = this.updateLoanRenewUseCase.execute(updateLoanRenewCommand);

        final var loanV1ResponseDto = this.loanResponseMapper.toLoanV1Response(loan);

        return ResponseEntity.ok(loanV1ResponseDto);
    }
}
