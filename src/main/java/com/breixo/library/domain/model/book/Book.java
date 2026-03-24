package com.breixo.library.domain.model.book;

import java.time.LocalDateTime;

import com.breixo.library.domain.vo.Isbn;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/**
 * The Record Book.
 *
 * @param id              The id.
 * @param isbn            The isbn.
 * @param title           The title.
 * @param author          The author.
 * @param genre           The genre.
 * @param totalCopies     The total copies.
 * @param availableCopies The available copies.
 * @param createdAt       The created at.
 * @param updatedAt       The updated at.
 */
@Builder
public record Book(@NotNull Long id, @NotNull Isbn isbn, @NotBlank String title, @NotBlank String author,
                   @NotBlank String genre, @NotNull Integer totalCopies, @NotNull Integer availableCopies,
                   @NotNull LocalDateTime createdAt, @NotNull LocalDateTime updatedAt) {

}
