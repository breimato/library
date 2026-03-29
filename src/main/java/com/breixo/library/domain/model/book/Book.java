package com.breixo.library.domain.model.book;

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
 */
@Builder
public record Book(@NotNull Integer id, @NotNull Isbn isbn, @NotBlank String title, @NotBlank String author,
                   @NotBlank String genre, @NotNull Integer totalCopies, @NotNull Integer availableCopies) {

}
