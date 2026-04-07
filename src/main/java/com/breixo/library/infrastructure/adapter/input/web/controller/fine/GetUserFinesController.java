package com.breixo.library.infrastructure.adapter.input.web.controller.fine;

import com.breixo.library.domain.port.input.fine.GetUserFinesUseCase;
import com.breixo.library.infrastructure.adapter.input.web.api.GetUserFinesV1Api;
import com.breixo.library.infrastructure.adapter.input.web.dto.GetFinesV1ResponseDto;
import com.breixo.library.infrastructure.adapter.input.web.mapper.fine.FineMapper;
import com.breixo.library.infrastructure.adapter.input.web.mapper.fine.GetUserFinesRequestMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/** The Class Get User Fines Controller. */
@RestController
@RequiredArgsConstructor
public class GetUserFinesController implements GetUserFinesV1Api {

    /** The get user fines use case. */
    private final GetUserFinesUseCase getUserFinesUseCase;

    /** The get user fines request mapper. */
    private final GetUserFinesRequestMapper getUserFinesRequestMapper;

    /** The fine mapper. */
    private final FineMapper fineMapper;

    /** {@inheritDoc} */
    @Override
    public ResponseEntity<GetFinesV1ResponseDto> getUserFinesV1(final Integer id, final Integer xRequesterId) {

        final var getUserFinesCommand = this.getUserFinesRequestMapper.toGetUserFinesCommand(xRequesterId, id);

        final var fines = this.getUserFinesUseCase.execute(getUserFinesCommand);

        final var fineV1List = this.fineMapper.toFineV1List(fines);

        final var getFinesV1ResponseDto = GetFinesV1ResponseDto.builder().fines(fineV1List).build();

        return ResponseEntity.ok(getFinesV1ResponseDto);
    }
}
