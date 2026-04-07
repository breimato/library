package com.breixo.library.domain.port.input.user;

import com.breixo.library.domain.model.user.enums.UserRole;
import jakarta.validation.constraints.NotNull;

/** The Interface Authorization Service. */
public interface AuthorizationService {

    /**
     * Require minimum role.
     *
     * @param requesterId the requester id
     * @param minimumRole the minimum role required (inclusive)
     */
    void requireMinimumRole(@NotNull Integer requesterId, @NotNull UserRole minimumRole);

    /**
     * Require own resource or role.
     *
     * @param requesterId the requester id
     * @param resourceOwnerId the resource owner id
     * @param minimumRole the minimum role to bypass ownership restriction
     */
    void requireOwnResourceOrRole(@NotNull Integer requesterId, @NotNull Integer resourceOwnerId, @NotNull UserRole minimumRole);
}
