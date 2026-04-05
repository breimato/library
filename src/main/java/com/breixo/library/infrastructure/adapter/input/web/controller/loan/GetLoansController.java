package com.breixo.library.infrastructure.adapter.input.web.controller.loan;

import com.breixo.library.domain.command.loan.LoanSearchCriteriaCommand;
import com.breixo.library.domain.model.user.enums.UserRole;
import com.breixo.library.domain.port.output.loan.LoanRetrievalPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.api.GetLoansV1Api;
import com.breixo.library.infrastructure.adapter.input.web.dto.GetLoansV1Request;
import com.breixo.library.infrastructure.adapter.input.web.dto.GetLoansV1ResponseDto;
import com.breixo.library.infrastructure.adapter.input.web.mapper.loan.GetLoansV1RequestMapper;
import com.breixo.library.infrastructure.adapter.input.web.mapper.loan.LoanMapper;
import com.breixo.library.infrastructure.adapter.input.web.security.AuthenticatedUserContextHelper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/** The Class Get Loans Controller. */
@RestController
@RequiredArgsConstructor
public class GetLoansController implements GetLoansV1Api {

    /** The loan retrieval persistence port. */
    private final LoanRetrievalPersistencePort loanRetrievalPersistencePort;

    /** The get loans V1 request mapper. */
    private final GetLoansV1RequestMapper getLoansV1RequestMapper;

    /** The loan mapper. */
    private final LoanMapper loanMapper;

    /** {@inheritDoc} */
    @Override
    public ResponseEntity<GetLoansV1ResponseDto> getLoansV1(final GetLoansV1Request getLoansV1Request) {

        var loanSearchCriteriaCommand = this.getLoansV1RequestMapper.toLoanSearchCriteriaCommand(getLoansV1Request);

        if (UserRole.NORMAL.equals(AuthenticatedUserContextHelper.getAuthenticatedUserRole())) {
            loanSearchCriteriaCommand = LoanSearchCriteriaCommand.builder()
                    .id(loanSearchCriteriaCommand.getId())
                    .bookId(loanSearchCriteriaCommand.getBookId())
                    .statusId(loanSearchCriteriaCommand.getStatusId())
                    .userId(AuthenticatedUserContextHelper.getAuthenticatedUserId())
                    .build();
        }

        final var loans = this.loanRetrievalPersistencePort.find(loanSearchCriteriaCommand);

        final var loanV1DtoList = this.loanMapper.toLoanV1List(loans);

        final var getLoansV1ResponseDto = GetLoansV1ResponseDto.builder().loans(loanV1DtoList).build();

        return ResponseEntity.ok(getLoansV1ResponseDto);
    }
}
