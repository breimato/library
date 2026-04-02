package com.breixo.library.infrastructure.mapper;

import java.util.Objects;

import com.breixo.library.domain.model.user.enums.UserRole;

import org.mapstruct.Mapper;

/** The Interface User Role Mapper. */
@Mapper(componentModel = "spring")
public interface UserRoleMapper {

    /**
     * To role id.
     *
     * @param userRole the user role.
     * @return the integer value.
     */
    default Integer toRoleId(final UserRole userRole) {
        return Objects.nonNull(userRole) ? userRole.getId() : UserRole.NORMAL.getId();
    }

    /**
     * To user role.
     *
     * @param id the role integer id.
     * @return the user role.
     */
    default UserRole toUserRole(final Integer id) {
        return UserRole.values()[id];
    }
}
