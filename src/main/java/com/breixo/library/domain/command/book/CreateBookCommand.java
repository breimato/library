package com.breixo.library.domain.command.book;

import com.breixo.library.domain.vo.Isbn;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * The Record Create Book Command.
 *
 * @param isbn            The isbn.
 * @param title           The title.
 * @param author          The author.
 * @param genre           The genre.
 * @param totalCopies     The total copies.
 * @param availableCopies The available copies.
 */
public record CreateBookCommand(@NotNull Isbn isbn, @NotBlank String title, @NotBlank String author,
                                @NotBlank String genre, @NotNull Integer totalCopies,
                                @NotNull Integer availableCopies) {

}
