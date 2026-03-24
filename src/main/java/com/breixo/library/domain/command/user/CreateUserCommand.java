package com.breixo.library.domain.command.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

/**
 * The Record Create User Command.
 *
 * @param name  The name.
 * @param email The email.
 * @param phone The phone.
 */
@Builder
public record CreateUserCommand(@NotBlank String name, @NotBlank String email, String phone) {

}
