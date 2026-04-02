package com.breixo.library.infrastructure.adapter.input.web.controller.loanrequest;

import com.breixo.library.domain.port.input.loanrequest.CreateLoanRequestUseCase;
import com.breixo.library.infrastructure.adapter.input.web.api.PostLoanRequestV1Api;
import com.breixo.library.infrastructure.adapter.input.web.dto.LoanRequestV1Response;
import com.breixo.library.infrastructure.adapter.input.web.dto.PostLoanRequestV1Request;
import com.breixo.library.infrastructure.adapter.input.web.mapper.loanrequest.LoanRequestResponseMapper;
import com.breixo.library.infrastructure.adapter.input.web.mapper.loanrequest.PostLoanRequestRequestMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/** The Class Post Loan Request Controller. */
@RestController
@RequiredArgsConstructor
public class PostLoanRequestController implements PostLoanRequestV1Api {

    /** The create loan request use case. */
    private final CreateLoanRequestUseCase createLoanRequestUseCase;

    /** The post loan request request mapper. */
    private final PostLoanRequestRequestMapper postLoanRequestRequestMapper;

    /** The loan request response mapper. */
    private final LoanRequestResponseMapper loanRequestResponseMapper;

    /** {@inheritDoc} */
    @Override
    public ResponseEntity<LoanRequestV1Response> postLoanRequestV1(final PostLoanRequestV1Request postLoanRequestV1Request) {

        final var createLoanRequestCommand =
                this.postLoanRequestRequestMapper.toCreateLoanRequestCommand(postLoanRequestV1Request);

        final var loanRequest = this.createLoanRequestUseCase.execute(createLoanRequestCommand);

        final var loanRequestV1Response = this.loanRequestResponseMapper.toLoanRequestV1Response(loanRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(loanRequestV1Response);
    }
}
