package com.breixo.library.domain.model.user;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * The Record User.
 *
 * @param id                The id.
 * @param name              The name.
 * @param email             The email.
 * @param phone             The phone.
 * @param membershipExpires The membership expires.
 * @param status            The status.
 * @param createdAt         The created at.
 * @param updatedAt         The updated at.
 */
public record User(@NotNull Long id, @NotBlank String name, @NotBlank String email, String phone,
                   @NotNull LocalDate membershipExpires, @NotNull UserStatus status,
                   @NotNull LocalDateTime createdAt, @NotNull LocalDateTime updatedAt) {

}
