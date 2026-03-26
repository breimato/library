package com.breixo.library.infrastructure.adapter.output.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

/** The Class Loan Entity. */
@Data
public class LoanEntity {

    /** The id. */
    private Integer id;

    /** The user id. */
    private Integer userId;

    /** The book id. */
    private Integer bookId;

    /** The due date. */
    private LocalDate dueDate;

    /** The return date. */
    private LocalDate returnDate;

    /** The status id. */
    private Integer statusId;

    /** The created at. */
    private LocalDateTime createdAt;

    /** The updated at. */
    private LocalDateTime updatedAt;
}
