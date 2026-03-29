package com.breixo.library.infrastructure.mapper;

import com.breixo.library.domain.model.user.enums.UserStatus;

import org.mapstruct.Mapper;

/** The Interface User Status Mapper. */
@Mapper(componentModel = "spring")
public interface UserStatusMapper {

    /**
     * To status value.
     *
     * @param userStatus the user status.
     * @return the integer value.
     */
    default Integer toStatusId(final UserStatus userStatus) {
        return userStatus.getId();
    }

    /**
     * To user status.
     *
     * @param id the status integer id.
     * @return the user status.
     */
    default UserStatus toUserStatus(final Integer id) {
        return UserStatus.values()[id];
    }
}
