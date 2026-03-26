package com.breixo.library.domain.command.user;

import com.breixo.library.domain.model.user.UserStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/**
 * The Record Update User Command.
 *
 * @param id     The id.
 * @param name   The name.
 * @param phone  The phone.
 * @param status The status.
 */
@Builder
public record UpdateUserCommand(@NotNull Integer id, String name, String phone, UserStatus status) {

}
