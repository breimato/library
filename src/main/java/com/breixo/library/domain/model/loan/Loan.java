package com.breixo.library.domain.model.loan;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.breixo.library.domain.model.loan.enums.LoanStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/**
 * The Record Loan.
 *
 * @param id         The id.
 * @param userId     The user id.
 * @param bookId     The book id.
 * @param dueDate    The due date.
 * @param returnDate The return date.
 * @param status     The status.
 * @param createdAt  The created at.
 * @param updatedAt  The updated at.
 */
@Builder
public record Loan(@NotNull Integer id, @NotNull Integer userId, @NotNull Integer bookId,
                   @NotNull LocalDate dueDate, LocalDate returnDate, @NotNull LoanStatus status,
                   @NotNull LocalDateTime createdAt, @NotNull LocalDateTime updatedAt) {
}
