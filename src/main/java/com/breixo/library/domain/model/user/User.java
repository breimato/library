package com.breixo.library.domain.model.user;

import com.breixo.library.domain.model.user.enums.UserRole;
import com.breixo.library.domain.model.user.enums.UserStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/**
 * The Record User.
 *
 * @param id           The id.
 * @param name         The name.
 * @param email        The email.
 * @param phone        The phone.
 * @param status       The status.
 * @param role         The role.
 * @param passwordHash The password hash.
 */
@Builder
public record User(@NotNull Integer id, @NotBlank String name, @NotBlank String email, String phone,
                   @NotNull UserStatus status, @NotNull UserRole role, String passwordHash) {

}
