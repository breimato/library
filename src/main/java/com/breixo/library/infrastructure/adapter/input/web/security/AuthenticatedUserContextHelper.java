package com.breixo.library.infrastructure.adapter.input.web.security;

import com.breixo.library.domain.model.user.enums.UserRole;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

/** The Class Authenticated User Context Helper. */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthenticatedUserContextHelper {

    /**
     * Get the authenticated user id from the security context.
     *
     * @return the authenticated user id
     */
    public static Integer getAuthenticatedUserId() {
        return (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * Get the authenticated user role from the security context.
     *
     * @return the authenticated user role
     */
    public static UserRole getAuthenticatedUserRole() {
        final var authority = SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().iterator().next().getAuthority();
        return UserRole.valueOf(authority.replace("ROLE_", ""));
    }
}
