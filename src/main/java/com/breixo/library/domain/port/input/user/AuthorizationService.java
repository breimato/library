package com.breixo.library.domain.port.input.user;

import com.breixo.library.domain.model.user.enums.UserRole;
import jakarta.validation.constraints.NotNull;

/** The Interface Authorization Service. */
public interface AuthorizationService {

    /**
     * Require role.
     *
     * @param requesterId the requester id
     * @param requiredRole the required role
     */
    void requireRole(@NotNull Integer requesterId, @NotNull UserRole requiredRole);

    /**
     * Require own resource or role.
     *
     * @param requesterId the requester id
     * @param resourceOwnerId the resource owner id
     * @param minimumRole the minimum role to bypass ownership restriction
     */
    void requireOwnResourceOrRole(@NotNull Integer requesterId, @NotNull Integer resourceOwnerId, @NotNull UserRole minimumRole);
}
