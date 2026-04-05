package com.breixo.library.infrastructure.adapter.output.entities;

import lombok.Data;

/** The Class User Entity. */
@Data
public class UserEntity {

    /** The id. */
    private Integer id;

    /** The name. */
    private String name;

    /** The email. */
    private String email;

    /** The phone. */
    private String phone;

    /** The status id. */
    private Integer statusId;

    /** The role id. */
    private Integer roleId;

    /** The password hash. */
    private String passwordHash;
}
