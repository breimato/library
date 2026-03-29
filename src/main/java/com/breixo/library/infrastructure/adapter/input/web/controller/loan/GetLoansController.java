package com.breixo.library.infrastructure.adapter.input.web.controller.loan;

import com.breixo.library.domain.command.loan.LoanSearchCriteriaCommand;
import com.breixo.library.domain.port.output.loan.LoanRetrievalPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.api.GetLoansV1Api;
import com.breixo.library.infrastructure.adapter.input.web.dto.GetLoansV1ResponseDto;
import com.breixo.library.infrastructure.adapter.input.web.mapper.loan.LoanMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/** The Class Get Loans Controller. */
@RestController
@RequiredArgsConstructor
public class GetLoansController implements GetLoansV1Api {

    /** The loan retrieval persistence port. */
    private final LoanRetrievalPersistencePort loanRetrievalPersistencePort;

    /** The loan mapper. */
    private final LoanMapper loanMapper;

    /** {@inheritDoc} */
    @Override
    public ResponseEntity<GetLoansV1ResponseDto> getLoansV1(final Integer userId, final Integer bookId, final Integer status) {

        final var loanSearchCriteriaCommand = LoanSearchCriteriaCommand.builder()
                .userId(userId).bookId(bookId).statusId(status).build();

        final var loans = this.loanRetrievalPersistencePort.findAll(loanSearchCriteriaCommand);

        final var loanV1DtoList = this.loanMapper.toLoanV1List(loans);

        final var getLoansV1ResponseDto = GetLoansV1ResponseDto.builder().loans(loanV1DtoList).build();

        return ResponseEntity.ok(getLoansV1ResponseDto);
    }
}
