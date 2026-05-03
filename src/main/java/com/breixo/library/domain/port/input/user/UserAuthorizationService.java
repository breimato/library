package com.breixo.library.domain.port.input.user;

import com.breixo.library.domain.model.user.enums.UserRole;
import jakarta.validation.constraints.NotNull;

/** The Interface User Authorization Service. */
public interface UserAuthorizationService {

    /**
     * Require access by minimum role.
     *
     * @param requesterId the requester id
     * @param userRole the minimum role required (inclusive)
     */
    void requireAccess(@NotNull Integer requesterId, @NotNull UserRole userRole);

    /**
     * Require access by resource ownership or minimum role.
     *
     * @param requesterId the requester id
     * @param resourceOwnerId the resource owner id
     * @param userRole the minimum role to bypass ownership restriction
     */
    void requireAccess(@NotNull Integer requesterId, @NotNull Integer resourceOwnerId, @NotNull UserRole userRole);
}
