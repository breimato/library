package com.breixo.library.infrastructure.adapter.input.web.controller.loan;

import com.breixo.library.domain.command.loan.LoanSearchCriteriaCommand;
import com.breixo.library.domain.exception.LoanException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.port.output.loan.LoanRetrievalPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.api.GetLoanIdV1Api;
import com.breixo.library.infrastructure.adapter.input.web.dto.LoanV1ResponseDto;
import com.breixo.library.infrastructure.adapter.input.web.mapper.loan.LoanResponseMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/** The Class Get Loan Id Controller. */
@RestController
@RequiredArgsConstructor
public class GetLoanIdController implements GetLoanIdV1Api {

    /** The loan retrieval persistence port. */
    private final LoanRetrievalPersistencePort loanRetrievalPersistencePort;

    /** The loan response mapper. */
    private final LoanResponseMapper loanResponseMapper;

    /** {@inheritDoc} */
    @Override
    public ResponseEntity<LoanV1ResponseDto> getLoanIdV1(final Integer id) {

        final var loanSearchCriteriaCommand = LoanSearchCriteriaCommand.builder().id(id).build();

        final var loan = this.loanRetrievalPersistencePort.find(loanSearchCriteriaCommand)
                .orElseThrow(() -> new LoanException(
                        ExceptionMessageConstants.LOAN_NOT_FOUND_CODE_ERROR,
                        ExceptionMessageConstants.LOAN_NOT_FOUND_MESSAGE_ERROR));

        final var loanV1ResponseDto = this.loanResponseMapper.toLoanV1Response(loan);

        return ResponseEntity.ok(loanV1ResponseDto);
    }
}
