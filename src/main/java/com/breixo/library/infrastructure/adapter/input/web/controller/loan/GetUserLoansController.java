package com.breixo.library.infrastructure.adapter.input.web.controller.loan;

import com.breixo.library.domain.port.input.loan.GetUserLoansUseCase;
import com.breixo.library.infrastructure.adapter.input.web.api.GetUserLoansV1Api;
import com.breixo.library.infrastructure.adapter.input.web.dto.GetLoansV1ResponseDto;
import com.breixo.library.infrastructure.adapter.input.web.mapper.loan.GetUserLoansRequestMapper;
import com.breixo.library.infrastructure.adapter.input.web.mapper.loan.LoanMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/** The Class Get User Loans Controller. */
@RestController
@RequiredArgsConstructor
public class GetUserLoansController implements GetUserLoansV1Api {

    /** The get user loans use case. */
    private final GetUserLoansUseCase getUserLoansUseCase;

    /** The get user loans request mapper. */
    private final GetUserLoansRequestMapper getUserLoansRequestMapper;

    /** The loan mapper. */
    private final LoanMapper loanMapper;

    /** {@inheritDoc} */
    @Override
    public ResponseEntity<GetLoansV1ResponseDto> getUserLoansV1(final Integer id, final Integer xRequesterId) {

        final var getUserLoansCommand = this.getUserLoansRequestMapper.toGetUserLoansCommand(xRequesterId, id);

        final var loans = this.getUserLoansUseCase.execute(getUserLoansCommand);

        final var loanV1DtoList = this.loanMapper.toLoanV1List(loans);

        final var getLoansV1ResponseDto = GetLoansV1ResponseDto.builder().loans(loanV1DtoList).build();

        return ResponseEntity.ok(getLoansV1ResponseDto);
    }
}
