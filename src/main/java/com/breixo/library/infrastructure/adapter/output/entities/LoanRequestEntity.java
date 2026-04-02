package com.breixo.library.infrastructure.adapter.output.entities;

import java.time.LocalDate;

import lombok.Data;

/** The Class Loan Request Entity. */
@Data
public class LoanRequestEntity {

    /** The id. */
    private Integer id;

    /** The user id. */
    private Integer userId;

    /** The book id. */
    private Integer bookId;

    /** The request date. */
    private LocalDate requestDate;

    /** The approval date. */
    private LocalDate approvalDate;

    /** The status id. */
    private Integer statusId;

    /** The rejection reason. */
    private String rejectionReason;
}
