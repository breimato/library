package com.breixo.library.infrastructure.adapter.input.web.controller.fine;

import com.breixo.library.domain.command.fine.FineSearchCriteriaCommand;
import com.breixo.library.domain.model.user.enums.UserRole;
import com.breixo.library.domain.port.output.fine.FineRetrievalPersistencePort;
import com.breixo.library.infrastructure.adapter.input.web.api.GetFinesV1Api;
import com.breixo.library.infrastructure.adapter.input.web.dto.GetFinesV1Request;
import com.breixo.library.infrastructure.adapter.input.web.dto.GetFinesV1ResponseDto;
import com.breixo.library.infrastructure.adapter.input.web.mapper.fine.FineMapper;
import com.breixo.library.infrastructure.adapter.input.web.mapper.fine.GetFinesV1RequestMapper;
import com.breixo.library.infrastructure.adapter.input.web.security.AuthenticatedUserContextHelper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/** The Class Get Fines Controller. */
@RestController
@RequiredArgsConstructor
public class GetFinesController implements GetFinesV1Api {

    /** The fine retrieval persistence port. */
    private final FineRetrievalPersistencePort fineRetrievalPersistencePort;

    /** The get fines V1 request mapper. */
    private final GetFinesV1RequestMapper getFinesV1RequestMapper;

    /** The fine mapper. */
    private final FineMapper fineMapper;

    /** {@inheritDoc} */
    @Override
    public ResponseEntity<GetFinesV1ResponseDto> getFinesV1(final GetFinesV1Request getFinesV1Request) {

        var fineSearchCriteriaCommand = this.getFinesV1RequestMapper.toFineSearchCriteriaCommand(getFinesV1Request);

        if (UserRole.NORMAL.equals(AuthenticatedUserContextHelper.getAuthenticatedUserRole())) {
            fineSearchCriteriaCommand = FineSearchCriteriaCommand.builder()
                    .id(fineSearchCriteriaCommand.getId())
                    .loanId(fineSearchCriteriaCommand.getLoanId())
                    .statusId(fineSearchCriteriaCommand.getStatusId())
                    .userId(AuthenticatedUserContextHelper.getAuthenticatedUserId())
                    .build();
        }

        final var fines = this.fineRetrievalPersistencePort.find(fineSearchCriteriaCommand);

        final var fineV1List = this.fineMapper.toFineV1List(fines);

        final var getFinesV1ResponseDto = GetFinesV1ResponseDto.builder().fines(fineV1List).build();

        return ResponseEntity.ok(getFinesV1ResponseDto);
    }
}
