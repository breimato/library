package com.breixo.library.infrastructure.adapter.output.entities;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/** The Class User Entity. */
@Data
public class UserEntity {

    /** The id. */
    private Long id;

    /** The name. */
    private String name;

    /** The email. */
    private String email;

    /** The phone. */
    private String phone;

    /** The membership expires. */
    private LocalDate membershipExpires;

    /** The status. */
    private String status;

    /** The created at. */
    private LocalDateTime createdAt;

    /** The updated at. */
    private LocalDateTime updatedAt;
}
