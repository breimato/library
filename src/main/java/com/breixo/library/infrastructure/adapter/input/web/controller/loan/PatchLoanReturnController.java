package com.breixo.library.infrastructure.adapter.input.web.controller.loan;

import com.breixo.library.domain.port.input.loan.UpdateLoanReturnUseCase;
import com.breixo.library.infrastructure.adapter.input.web.api.PatchLoanReturnV1Api;
import com.breixo.library.infrastructure.adapter.input.web.dto.LoanV1ResponseDto;
import com.breixo.library.infrastructure.adapter.input.web.dto.PatchLoanReturnV1Request;
import com.breixo.library.infrastructure.adapter.input.web.mapper.loan.LoanResponseMapper;
import com.breixo.library.infrastructure.adapter.input.web.mapper.loan.PatchLoanReturnRequestMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/** The Class Patch Loan Return Controller. */
@RestController
@RequiredArgsConstructor
public class PatchLoanReturnController implements PatchLoanReturnV1Api {

    /** The update loan return use case. */
    private final UpdateLoanReturnUseCase updateLoanReturnUseCase;

    /** The patch loan return request mapper. */
    private final PatchLoanReturnRequestMapper patchLoanReturnRequestMapper;

    /** The loan response mapper. */
    private final LoanResponseMapper loanResponseMapper;

    /** {@inheritDoc} */
    @Override
    public ResponseEntity<LoanV1ResponseDto> patchLoanReturnV1(final Integer id,
                                                               final PatchLoanReturnV1Request patchLoanReturnV1Request) {
        final var loanReturnCommand = this.patchLoanReturnRequestMapper.toLoanReturnCommand(id, patchLoanReturnV1Request);
        final var loan = this.updateLoanReturnUseCase.execute(loanReturnCommand);
        final var loanV1ResponseDto = this.loanResponseMapper.toLoanV1Response(loan);
        return ResponseEntity.ok(loanV1ResponseDto);
    }
}
