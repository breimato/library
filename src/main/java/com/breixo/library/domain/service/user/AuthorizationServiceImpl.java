package com.breixo.library.domain.service.user;

import com.breixo.library.domain.exception.ForbiddenActionException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.user.enums.UserRole;
import com.breixo.library.domain.port.input.user.AuthorizationService;
import com.breixo.library.domain.port.output.user.UserRetrievalPersistencePort;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;

/** The Class Authorization Service Impl. */
@Service
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {

    /** The user retrieval persistence port. */
    private final UserRetrievalPersistencePort userRetrievalPersistencePort;

    /** {@inheritDoc} */
    @Override
    public void requireMinimumRole(@NotNull final Integer requesterId, @NotNull final UserRole minimumRole) {

        final var user = this.userRetrievalPersistencePort.findById(requesterId);

        final var hasEnoughRole = user.role().getId() >= minimumRole.getId();

        if (BooleanUtils.isFalse(hasEnoughRole)) {
            throw new ForbiddenActionException(
                    ExceptionMessageConstants.FORBIDDEN_ACTION_CODE_ERROR,
                    ExceptionMessageConstants.FORBIDDEN_ACTION_MESSAGE_ERROR);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void requireOwnResourceOrRole(@NotNull final Integer requesterId, @NotNull final Integer resourceOwnerId,
            @NotNull final UserRole minimumRole) {

        final var user = this.userRetrievalPersistencePort.findById(requesterId);

        final var isOwner = requesterId.equals(resourceOwnerId);
        final var hasEnoughRole = user.role().getId() >= minimumRole.getId();

        if (BooleanUtils.isFalse(isOwner) && BooleanUtils.isFalse(hasEnoughRole)) {
            throw new ForbiddenActionException(
                    ExceptionMessageConstants.FORBIDDEN_OWN_RESOURCE_CODE_ERROR,
                    ExceptionMessageConstants.FORBIDDEN_OWN_RESOURCE_MESSAGE_ERROR);
        }
    }

}
