package com.breixo.library.infrastructure.adapter.input.web.controller.loan;

import com.breixo.library.domain.model.loan.Loan;
import com.breixo.library.domain.port.input.loan.CreateLoanUseCase;
import com.breixo.library.infrastructure.adapter.input.web.api.PostLoanV1Api;
import com.breixo.library.infrastructure.adapter.input.web.dto.LoanV1ResponseDto;
import com.breixo.library.infrastructure.adapter.input.web.dto.PostLoanV1Request;
import com.breixo.library.infrastructure.adapter.input.web.mapper.loan.LoanResponseMapper;
import com.breixo.library.infrastructure.adapter.input.web.mapper.loan.PostLoanRequestMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/** The Class Post Loan Controller. */
@RestController
@RequiredArgsConstructor
public class PostLoanController implements PostLoanV1Api {

    /** The create loan use case. */
    private final CreateLoanUseCase createLoanUseCase;

    /** The post loan request mapper. */
    private final PostLoanRequestMapper postLoanRequestMapper;

    /** The loan response mapper. */
    private final LoanResponseMapper loanResponseMapper;

    /** {@inheritDoc} */
    @Override
    public ResponseEntity<LoanV1ResponseDto> postLoanV1(final PostLoanV1Request postLoanV1Request) {

        final var createLoanCommand = this.postLoanRequestMapper.toCreateLoanCommand(postLoanV1Request);

        final var loan = this.createLoanUseCase.execute(createLoanCommand);

        final var loanV1ResponseDto = this.loanResponseMapper.toLoanV1Response(loan);

        return ResponseEntity.status(HttpStatus.CREATED).body(loanV1ResponseDto);
    }
}
