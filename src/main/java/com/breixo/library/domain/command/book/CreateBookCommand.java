package com.breixo.library.domain.command.book;

import com.breixo.library.domain.vo.Isbn;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/**
 * The Record Create Book Command.
 *
 * @param requesterId     The requester id.
 * @param isbn            The isbn.
 * @param title           The title.
 * @param author          The author.
 * @param genre           The genre.
 * @param totalCopies     The total copies.
 * @param availableCopies The available copies.
 */
@Builder
public record CreateBookCommand(@NotNull Integer requesterId, @NotNull Isbn isbn, @NotBlank String title, @NotBlank String author,
                                @NotBlank String genre, @NotNull Integer totalCopies,
                                @NotNull Integer availableCopies) {

}
