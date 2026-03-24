package com.breixo.library.domain.model.user;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/**
 * The Record User.
 *
 * @param id        The id.
 * @param name      The name.
 * @param email     The email.
 * @param phone     The phone.
 * @param status    The status.
 * @param createdAt The created at.
 * @param updatedAt The updated at.
 */
@Builder
public record User(@NotNull Long id, @NotBlank String name, @NotBlank String email, String phone,
                   @NotNull UserStatus status,
                   @NotNull LocalDateTime createdAt, @NotNull LocalDateTime updatedAt) {

}
