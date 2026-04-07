package com.breixo.library.application.usecase.fine;

import java.util.List;

import com.breixo.library.domain.command.fine.GetUserFinesCommand;
import com.breixo.library.domain.model.fine.Fine;
import com.breixo.library.domain.model.user.enums.UserRole;
import com.breixo.library.domain.port.input.fine.GetUserFinesUseCase;
import com.breixo.library.domain.port.input.user.AuthorizationService;
import com.breixo.library.domain.port.output.fine.FineRetrievalPersistencePort;
import com.breixo.library.domain.port.output.user.UserRetrievalPersistencePort;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/** The Class Get User Fines Use Case Impl. */
@Component
@RequiredArgsConstructor
public class GetUserFinesUseCaseImpl implements GetUserFinesUseCase {

    /** The authorization service. */
    private final AuthorizationService authorizationService;

    /** The user retrieval persistence port. */
    private final UserRetrievalPersistencePort userRetrievalPersistencePort;

    /** The fine retrieval persistence port. */
    private final FineRetrievalPersistencePort fineRetrievalPersistencePort;

    /** {@inheritDoc} */
    @Override
    public List<Fine> execute(@Valid @NotNull final GetUserFinesCommand getUserFinesCommand) {

        this.authorizationService.requireOwnResourceOrRole(
                getUserFinesCommand.requesterId(),
                getUserFinesCommand.userId(),
                UserRole.MANAGER);

        this.userRetrievalPersistencePort.findById(getUserFinesCommand.userId());

        return this.fineRetrievalPersistencePort.findByUserId(getUserFinesCommand.userId());
    }
}
