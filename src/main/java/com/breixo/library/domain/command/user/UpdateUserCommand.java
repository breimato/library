package com.breixo.library.domain.command.user;

import com.breixo.library.domain.model.user.enums.UserRole;
import com.breixo.library.domain.model.user.enums.UserStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/**
 * The Record Update User Command.
 *
 * @param id     The id.
 * @param name   The name.
 * @param phone  The phone.
 * @param status The status.
 * @param role   The role.
 */
@Builder
public record UpdateUserCommand(@NotNull Integer id, String name, String phone, UserStatus status, UserRole role) {

}
