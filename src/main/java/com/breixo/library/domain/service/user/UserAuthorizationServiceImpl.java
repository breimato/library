package com.breixo.library.domain.service.user;

import java.util.EnumSet;

import com.breixo.library.domain.exception.ForbiddenActionException;
import com.breixo.library.domain.exception.UserException;
import com.breixo.library.domain.exception.constants.ExceptionMessageConstants;
import com.breixo.library.domain.model.user.enums.UserRole;
import com.breixo.library.domain.port.input.user.UserAuthorizationService;
import com.breixo.library.domain.port.output.user.UserRetrievalPersistencePort;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;

/** The Class User Authorization Service Impl. */
@Service
@RequiredArgsConstructor
public class UserAuthorizationServiceImpl implements UserAuthorizationService {

    /** The user retrieval persistence port. */
    private final UserRetrievalPersistencePort userRetrievalPersistencePort;

    /** {@inheritDoc} */
    @Override
    public void requireAccess(@NotNull final Integer requesterId, @NotNull final UserRole userRole) {

        final var requesterRole = this.userRetrievalPersistencePort.findById(requesterId)
                .orElseThrow(() -> new UserException(
                        ExceptionMessageConstants.USER_NOT_FOUND_CODE_ERROR,
                        ExceptionMessageConstants.USER_NOT_FOUND_MESSAGE_ERROR))
                .role();

        final var hasEnoughRole = this.hasMinimumRole(requesterRole, userRole);

        if (BooleanUtils.isFalse(hasEnoughRole)) {
            throw new ForbiddenActionException(
                    ExceptionMessageConstants.FORBIDDEN_ACTION_CODE_ERROR,
                    ExceptionMessageConstants.FORBIDDEN_ACTION_MESSAGE_ERROR);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void requireAccess(@NotNull final Integer requesterId, @NotNull final Integer resourceOwnerId,
            @NotNull final UserRole userRole) {

        final var requesterRole = this.userRetrievalPersistencePort.findById(requesterId)
                .orElseThrow(() -> new UserException(
                        ExceptionMessageConstants.USER_NOT_FOUND_CODE_ERROR,
                        ExceptionMessageConstants.USER_NOT_FOUND_MESSAGE_ERROR))
                .role();

        final var isOwner = requesterId.equals(resourceOwnerId);
        final var hasEnoughRole = this.hasMinimumRole(requesterRole, userRole);

        if (BooleanUtils.isFalse(isOwner) && BooleanUtils.isFalse(hasEnoughRole)) {
            throw new ForbiddenActionException(
                    ExceptionMessageConstants.FORBIDDEN_OWN_RESOURCE_CODE_ERROR,
                    ExceptionMessageConstants.FORBIDDEN_OWN_RESOURCE_MESSAGE_ERROR);
        }
    }

    /**
     * Checks if user role satisfies minimum role.
     *
     * @param userRole the user role.
     * @param minimumRole the minimum role.
     * @return true, if successful.
     */
    private boolean hasMinimumRole(final UserRole userRole, final UserRole minimumRole) {
        return switch (minimumRole) {
            case NORMAL -> EnumSet.of(UserRole.NORMAL, UserRole.MANAGER, UserRole.ADMIN).contains(userRole);
            case MANAGER -> EnumSet.of(UserRole.MANAGER, UserRole.ADMIN).contains(userRole);
            case ADMIN -> UserRole.ADMIN.equals(userRole);
        };
    }
}
